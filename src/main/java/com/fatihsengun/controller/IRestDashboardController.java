package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoChartData;
import com.fatihsengun.dto.DtoDashboardSummary;
import com.fatihsengun.entity.RootResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IRestDashboardController {

    public RootResponseEntity<DtoDashboardSummary> dashboardSummary();
    public RootResponseEntity<List<DtoChartData>> getChartStatictics(LocalDateTime startDate, LocalDateTime endDate);
}
