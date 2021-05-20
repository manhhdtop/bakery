package com.bakery.server.model.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UploadFileResponse {
    private Long id;
    private Long referenceId;
    private String fileName;
    private String uri;
    private String fileType;
    private Long size;

    public static UploadFileResponse of(String uri) {
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        uploadFileResponse.uri = uri;

        return uploadFileResponse;
    }

    public static UploadFileResponse of(String fileName, String uri, String fileType, Long size) {
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        uploadFileResponse.fileName = fileName;
        uploadFileResponse.uri = uri;
        uploadFileResponse.fileType = fileType;
        uploadFileResponse.size = size;

        return uploadFileResponse;
    }
}
