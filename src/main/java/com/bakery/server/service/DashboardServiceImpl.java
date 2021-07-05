package com.bakery.server.service;

import com.bakery.server.constant.InvoiceStatus;
import com.bakery.server.entity.InvoiceEntity;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.DashboardOverviewResponse;
import com.bakery.server.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public ApiBaseResponse getDashboardOverview() {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        long startDate = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        List<InvoiceEntity> entities = invoiceRepository.findByCreatedDateAfter(startDate);
        long totalMoney = entities.stream().filter(e -> e.getStatus().equals(InvoiceStatus.SUCCESS.getStatus()))
                .mapToLong(InvoiceEntity::getTotalAmount).sum();
        int totalSuccess = (int) entities.stream().filter(e -> e.getStatus().equals(InvoiceStatus.SUCCESS.getStatus())).count();
        int totalPending = (int) entities.stream().filter(e -> e.getStatus().equals(InvoiceStatus.INIT.getStatus()) ||
                e.getStatus().equals(InvoiceStatus.CONFIRMED.getStatus()))
                .count();
        return ApiBaseResponse.success(DashboardOverviewResponse.builder()
                .totalMoney(totalMoney)
                .totalInvoice(entities.size())
                .totalSuccess(totalSuccess)
                .totalPending(totalPending)
                .build());
    }
}
