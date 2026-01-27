package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestDashboardController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoChartData;
import com.fatihsengun.dto.DtoDashboardSummary;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.DashboardSummaryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rest/api/dashboard")
public class RestDashboardController extends RestRootResponseController implements IRestDashboardController {


    @Autowired
    private DashboardSummaryImpl dashboardSummary;


    @Override
    @GetMapping("/summary")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RootResponseEntity<DtoDashboardSummary> dashboardSummary() {
        return ok(dashboardSummary.dashboardSummary());
    }

    @Override
    @GetMapping("/chart-data")
    public RootResponseEntity<List<DtoChartData>> getChartStatictics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return ok(dashboardSummary.getChartStatictics(startDate, endDate));
    }
}
