package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestOrderController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoOrder;
import com.fatihsengun.dto.DtoOrderUI;
import com.fatihsengun.dto.DtoStatusUI;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/order")

public class RestOrderControllerImpl extends RestRootResponseController implements IRestOrderController {

    @Autowired
    private OrderServiceImpl orderService;


    @Override

    @PostMapping("/create")
    public RootResponseEntity<DtoOrder> createOrder(@Valid @RequestBody DtoOrderUI dtoOrderUI) {
        return ok(orderService.createOrder(dtoOrderUI));
    }

    @Override
    @PutMapping("/admin/update-status/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public RootResponseEntity<DtoOrder> updateStatus(@Valid @PathVariable(name = "id") UUID orderId, @RequestBody DtoStatusUI dtoStatusUI) {
        return ok(orderService.updateStatus(orderId, dtoStatusUI));
    }

    @Override
    @GetMapping("/admin/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public RootResponseEntity<Page<DtoOrder>> getAllOrders(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, Sort sort) {
        return ok(orderService.getAllOrders(pageable));
    }

    @Override
    @GetMapping("/my-orders")
    public RootResponseEntity<List<DtoOrder>> getMyOrders() {
        return ok(orderService.getMyOrders());
    }
}
