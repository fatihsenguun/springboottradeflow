package com.fatihsengun.dto;

import com.fatihsengun.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoStatusUI {
    public OrderStatus orderStatus;
}
