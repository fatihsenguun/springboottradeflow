package com.fatihsengun.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventModel {
    private UUID orderId;
    private UUID userId;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

}
