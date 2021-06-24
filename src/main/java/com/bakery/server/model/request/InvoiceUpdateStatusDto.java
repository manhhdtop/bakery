package com.bakery.server.model.request;

import com.bakery.server.exception.BadRequestException;
import com.bakery.server.utils.AssertUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class InvoiceUpdateStatusDto {
    @NotNull
    private Long id;
    @NotNull
    private Integer status;
    private String description;

    public void validate(Integer invoiceStatus) {
        List<Integer> acceptStatus;
        switch (invoiceStatus) {
            case 0:
                acceptStatus = Arrays.asList(1, -1);
                AssertUtil.isTrue(acceptStatus.contains(status), "invoice.status.invalid");
                break;
            case 1:
                acceptStatus = Collections.singletonList(2);
                AssertUtil.isTrue(acceptStatus.contains(status), "invoice.status.invalid");
                break;
            case 2:
                acceptStatus = Arrays.asList(3, -2, -1);
                AssertUtil.isTrue(acceptStatus.contains(status), "invoice.status.invalid");
                break;
            default:
                throw new BadRequestException("invoice.status.invalid");
        }
    }
}
