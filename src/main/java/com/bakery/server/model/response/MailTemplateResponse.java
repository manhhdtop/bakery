package com.bakery.server.model.response;

import lombok.Data;

@Data
public class MailTemplateResponse {
    private Long id;
    private String code;
    private String name;
    private String subject;
    private String message;
    private Integer status;
}
