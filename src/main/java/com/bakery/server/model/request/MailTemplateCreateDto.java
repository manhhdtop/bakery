package com.bakery.server.model.request;

import com.bakery.server.utils.AssertUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MailTemplateCreateDto {
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
        String codePattern = "[A-Z][A-Z0-9_]{3,}[A-Z]";
        AssertUtil.isTrue(code.matches(codePattern), "mail_template.code.invalid");
        code = code.trim();
        name = name.trim();
        subject = subject.trim();
        message = message.trim();
    }
}
