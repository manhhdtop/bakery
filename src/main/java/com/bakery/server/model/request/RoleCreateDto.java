package com.bakery.server.model.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleCreateDto {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Integer status;
    @NotEmpty
    private List<Long> actionIds;

    public void validData() {
        code = code.trim().toUpperCase();
        name = name.trim();
        if (StringUtils.isNotBlank(description)) {
            description = description.trim();
        }
    }
}
