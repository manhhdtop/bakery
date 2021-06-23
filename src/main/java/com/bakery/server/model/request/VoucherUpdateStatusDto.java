package com.bakery.server.model.request;

import com.bakery.server.utils.AssertUtil;
import com.bakery.server.utils.Constant;
import com.bakery.server.utils.Utils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class VoucherUpdateStatusDto {
    @NotNull
    private Long id;
    @NotNull
    private Integer status;
}
