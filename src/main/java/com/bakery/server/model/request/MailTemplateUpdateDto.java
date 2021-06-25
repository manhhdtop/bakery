package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MailTemplateUpdateDto {
    @NotNull
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
    @NotNull
    private Integer status;

    public void validate() {
        name = name.trim();
        subject = subject.trim();
        message = message.trim();
    }
}
