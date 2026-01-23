package com.fatihsengun.dto;

import com.fatihsengun.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoProduct extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    private List<DtoCategory> categories;
    private List<DtoProduct> images;
}
