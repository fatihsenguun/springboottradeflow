package com.fatihsengun.service;

import com.fatihsengun.dto.DtoOrder;
import com.fatihsengun.dto.DtoOrderUI;
import com.fatihsengun.dto.DtoStatusUI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.UUID;

public interface IOrderService {

    public DtoOrder createOrder(DtoOrderUI dtoOrderUI);

    public DtoOrder updateStatus(UUID orderId, DtoStatusUI dtoStatusUI);

    Page<DtoOrder> getAllOrders(Pageable pageable);

}
