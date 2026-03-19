package com.fatihsengun.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCartUI {
    @NotEmpty
    private List<DtoCartItemUI> items;

    @Email(message = "Please provide a valid email address")
    private String guestEmail;
}
