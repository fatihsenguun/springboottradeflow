package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestDashboardController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoDashboardSummary;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.DashboardSummaryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
