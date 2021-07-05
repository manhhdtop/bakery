package com.bakery.server.controller.admin;

import com.bakery.server.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${admin-base-path}/dashboard")
@RestController
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("dashboard-overview")
    public ResponseEntity<?> getDashboardOverview() {
        return ResponseEntity.ok(dashboardService.getDashboardOverview());
    }

}
