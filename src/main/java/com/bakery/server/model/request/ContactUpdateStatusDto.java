package com.bakery.server.model.request;

import com.bakery.server.constant.ContactStatus;
import com.bakery.server.exception.BadRequestException;
import com.bakery.server.utils.AssertUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class ContactUpdateStatusDto {
    @NotNull
    private Long id;
    @NotNull
    private Integer status;
    private String description;

    public void validate() {
        List<Integer> statuses = Arrays.asList(ContactStatus.SUCCESS.getStatus(), ContactStatus.REJECT.getStatus());
        AssertUtil.isTrue(statuses.contains(status), "contact.status.invalid");
        if (ContactStatus.REJECT.getStatus() == status) {
            AssertUtil.isNotBlank(description, "contact.status_description.required");
        }
    }
}
