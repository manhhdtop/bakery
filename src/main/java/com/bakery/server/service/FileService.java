package com.bakery.server.service;

import com.bakery.server.exception.FileStorageException;
import com.bakery.server.model.response.UploadFileResponse;
import com.bakery.server.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Objects;

import static com.bakery.server.utils.MessageUtils.getMessage;

@Service
public class FileService {
    private static final String DEFAULT_FOLDER = "/uploads";

    private final Path fileStorageLocation;
    public String rootPath;
    public String uploadFolder;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        rootPath = fileStorageProperties.getRootPath();
        uploadFolder = fileStorageProperties.getUploadDir();
        this.fileStorageLocation = Paths.get(rootPath + uploadFolder).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public UploadFileResponse storeFile(MultipartFile file) {
        return storeFile(file, DEFAULT_FOLDER);
    }

    public UploadFileResponse storeFile(MultipartFile file, String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return storeFile(file, Paths.get(rootPath + uploadFolder + path).toAbsolutePath().normalize(), path);
    }

    public UploadFileResponse storeFile(MultipartFile file, String path, String filename) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return storeFile(file, Paths.get(rootPath + uploadFolder + path).toAbsolutePath().normalize(), path, filename);
    }

    private UploadFileResponse storeFile(MultipartFile file, Path path, String folderUpload) {
        return storeFile(file, path, folderUpload, null);
    }

    private UploadFileResponse storeFile(MultipartFile file, Path path, String folderUpload, String fileName) {
        if (fileName == null) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        }

        try {
            Files.createDirectories(path);
        } catch (Exception ex) {
            throw new FileStorageException("file.upload_folder.create_fail", ex);
        }

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("file.filename.invalid");
            }

            // Copy file to the target location (Replacing existing file with the same name)
            int index = fileName.lastIndexOf(".");
            String fileUpload;
            if (index == -1) {
                fileUpload = new Date().getTime() + "";
            } else {
                String fileExtension = fileName.substring(index + 1);
                fileUpload = new Date().getTime() + "." + fileExtension;
            }
            String fileDownloadUri = uploadFolder + folderUpload + "/" + fileUpload;

            Path targetLocation = path.toAbsolutePath().normalize().resolve(fileUpload);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return UploadFileResponse.of(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException(getMessage("file.store_file.error", fileName), ex);
        } catch (Exception e) {
            throw new FileStorageException(getMessage("file.invalid" + fileName));
        }
    }

}
