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
public class VoucherUpdateDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Long value;
    private Long minAmount;
    private Long maxAmount;
    private Long minRefund;
    private Long maxRefund;
    @NotNull
    private Integer type;
    @NotNull
    private Integer quantity;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;

    public void validate() {
        name = name.trim();
        if (description != null) {
            description = description.trim();
        }
        List<Integer> voucherType = Arrays.asList(Constant.VoucherType.AMOUNT, Constant.VoucherType.PERCENT);
        AssertUtil.isTrue(voucherType.contains(type), "voucher.type.notValid");
        AssertUtil.isTrue(quantity > 0, "voucher.quantity.mustGreaterThan0");
        AssertUtil.isTrue(value > 0, "voucher.value.mustGreaterThan0");
        if (minAmount != null && maxAmount != null) {
            AssertUtil.isTrue(type.equals(Constant.VoucherType.AMOUNT) && minAmount <= maxAmount, "voucher.maxAmount.notLessThanMinAmount");
        }
        if (minRefund != null || maxRefund != null) {
            if (minRefund != null) {
                if (type.equals(Constant.VoucherType.PERCENT)) {
                    AssertUtil.isTrue(minRefund <= value, "voucher.minRefund.lessThanValue");
                }
            }
            if (maxRefund != null) {
                if (type.equals(Constant.VoucherType.PERCENT)) {
                    AssertUtil.isTrue(maxRefund <= value, "voucher.maxRefund.notLessThanAmount");
                }
            }
            if (minRefund != null && maxRefund != null) {
                AssertUtil.isTrue(type.equals(Constant.VoucherType.PERCENT) && minRefund <= maxRefund, "voucher.maxRefund.notLessThanMinRefund");
            }
        }
        if (type.equals(Constant.VoucherType.PERCENT)) {
            minRefund = null;
            maxRefund = null;
        }
        AssertUtil.isTrue(startDate.before(endDate), "voucher.applyDate.startDateNotBeforeEndDate");
    }
}
