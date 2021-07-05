package com.bakery.server.service;

import com.bakery.server.model.request.ActionCreateDto;
import com.bakery.server.model.request.ActionUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface DashboardService {
    ApiBaseResponse getDashboardOverview();
}
