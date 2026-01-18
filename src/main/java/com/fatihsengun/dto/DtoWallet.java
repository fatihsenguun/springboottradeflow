package com.fatihsengun.dto;

import com.fatihsengun.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoWallet extends BaseEntity {
    private BigDecimal balance;

    private String currency;
}
