package com.bakery.server.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DashboardOverviewResponse {
    private Long totalMoney;
    private int totalInvoice;
    private int totalSuccess;
    private int totalPending;
}
