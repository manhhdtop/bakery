package com.bakery.server.model.request;

import com.bakery.server.model.response.UploadFileResponse;
import lombok.Data;

@Data
public class NewsAddRequest {
    private String name;
    private String slug;
    private String description;
    private String content;
    private UploadFileResponse imageUpload;
    private Integer read;
    private Integer like;
    private Integer status;

    public void validData() {
        this.read = 0;
        this.like = 0;
    }
}
