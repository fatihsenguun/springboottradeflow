package com.fatihsengun.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoLoginIU {

    @NotBlank(message = "The email field cannot be left blank.")
    @Email
    private String email;

    @NotBlank(message = "The password field cannot be left blank.")
    private String password;
}
