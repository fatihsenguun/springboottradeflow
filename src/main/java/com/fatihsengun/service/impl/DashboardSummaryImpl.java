package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoDashboardSummary;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.OrderRepository;
import com.fatihsengun.repository.ProductRepository;
import com.fatihsengun.service.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardSummaryImpl implements IDashboardService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private IGlobalMapper globalMapper;

    @Override
    public DtoDashboardSummary dashboardSummary() {

        DtoDashboardSummary summary = new DtoDashboardSummary();
        BigDecimal totalSales = orderRepository.sumTotalSales();

        summary.setTotalSales(totalSales != null ? totalSales : BigDecimal.ZERO);

        summary.setOrderCount(orderRepository.count());
        summary.setProductCount(productRepository.count());

        summary.setTopSellingProducts(globalMapper.toListDtoProduct(productRepository.findTop5ByOrderByTotalSalesCountDesc()));


        return summary;
    }
}
