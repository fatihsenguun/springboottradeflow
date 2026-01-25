package com.fatihsengun.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter

public class DtoDashboardSummary {
    private BigDecimal totalSales;
    private Long orderCount;
    private Long productCount;
    private List<DtoProduct> topSellingProducts;
    private List<DtoOrderItem> lastOrders;

}
