package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoDashboardSummary;
import com.fatihsengun.dto.DtoOrderItem;
import com.fatihsengun.dto.DtoProduct;
import com.fatihsengun.entity.OrderItem;
import com.fatihsengun.entity.Product;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.OrderItemRepository;
import com.fatihsengun.repository.OrderRepository;
import com.fatihsengun.repository.ProductRepository;
import com.fatihsengun.service.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardSummaryImpl implements IDashboardService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private IGlobalMapper globalMapper;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public DtoDashboardSummary dashboardSummary() {

        DtoDashboardSummary summary = new DtoDashboardSummary();
        BigDecimal totalSales = orderRepository.sumTotalSales();

        summary.setTotalSales(totalSales != null ? totalSales : BigDecimal.ZERO);

        summary.setOrderCount(orderRepository.count());
        summary.setProductCount(productRepository.count());

        List<Product> topProducts = productRepository.findTop5ByOrderByTotalSalesCountDesc();

        List<DtoProduct> topProductDtos = topProducts.stream().map(product -> {
            DtoProduct dto = globalMapper.toDtoProduct(product);
            BigDecimal earning = orderRepository.sumTotalEarningByProductId(product.getId());
            dto.setTotalEarning(earning != null ? earning : BigDecimal.ZERO);
            return dto;
        }).collect(Collectors.toList());


        List<OrderItem> lastOrders = orderItemRepository.findTop10ByOrderByCreatedAtDesc();

        List<DtoOrderItem> lastOrderDtos = lastOrders.stream().map(product -> {
            DtoOrderItem dto = globalMapper.toDtoOrderItem(product);
            return dto;
        }).collect(Collectors.toList());
        summary.setLastOrders(lastOrderDtos);


        summary.setTopSellingProducts(topProductDtos);
        return summary;

    }
}
