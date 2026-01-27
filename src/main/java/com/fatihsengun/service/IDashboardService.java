package com.fatihsengun.service;

import com.fatihsengun.dto.DtoChartData;
import com.fatihsengun.dto.DtoDashboardSummary;

import java.time.LocalDateTime;
import java.util.List;

public interface IDashboardService {

    public DtoDashboardSummary dashboardSummary();
    public List<DtoChartData> getChartStatictics(LocalDateTime startDate, LocalDateTime endDate);
}
