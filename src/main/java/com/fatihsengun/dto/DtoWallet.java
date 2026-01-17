package com.fatihsengun.dto;

import com.fatihsengun.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DtoWallet extends BaseEntity {
    private BigDecimal balance;

    private String currency;
}
