package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoDashboardSummary;
import com.fatihsengun.entity.RootResponseEntity;

public interface IRestDashboardController {

    public RootResponseEntity<DtoDashboardSummary> dashboardSummary();
}
