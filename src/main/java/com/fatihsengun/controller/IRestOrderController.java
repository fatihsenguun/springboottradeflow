package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoOrder;
import com.fatihsengun.dto.DtoOrderUI;
import com.fatihsengun.dto.DtoStatusUI;
import com.fatihsengun.entity.RootResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface IRestOrderController {

    public RootResponseEntity<DtoOrder> createOrder(DtoOrderUI dtoOrderUI);

    public RootResponseEntity<DtoOrder> updateStatus(UUID orderId, DtoStatusUI dtoStatusUI);

    public RootResponseEntity<Page<DtoOrder>> getAllOrders(Pageable pageable, Sort sort);

    public RootResponseEntity<List<DtoOrder>> getMyOrders();

    public RootResponseEntity<DtoOrder> getOrderById(UUID id);
}
