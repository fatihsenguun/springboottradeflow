package com.fatihsengun.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoWalletUI {

    @NotNull(message = "The amount to be loaded cannot be empty.")
    @DecimalMin(value = "10.00", message ="You can upload a minimum of 10 units." )
    private BigDecimal amount;

}
