package com.fatihsengun.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DtoWalletUI {

    private BigDecimal amount;

    private String currency;
}
