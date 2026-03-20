package com.fatihsengun.dto;

import com.fatihsengun.enums.PaymentType;
import com.fatihsengun.enums.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCheckoutRequest {

    private PaymentType paymentMethod;

    private String email;

}
