package com.fatihsengun.dto;

import com.fatihsengun.entity.BaseEntity;
import com.fatihsengun.entity.OrderItem;
import com.fatihsengun.entity.User;
import com.fatihsengun.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class DtoOrder extends BaseEntity {


    private BigDecimal totalAmount;

    private OrderStatus status;

    private List<DtoOrderItem> orderItemList;

    private String orderNumber;

}
