package com.fatihsengun.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoProductUI {

    @NotBlank(message = "Product name is required.")
    private  String name;

    private String description;

    @NotNull
    @DecimalMin(value = "0.01", message ="The price must be greater than 0." )
    private BigDecimal price;

    @NotNull
    @Min(value = 0, message = "Stock levels cannot be negative.")
    private Integer stock;

    @NotNull
    private List<UUID> categoryIds;

}
