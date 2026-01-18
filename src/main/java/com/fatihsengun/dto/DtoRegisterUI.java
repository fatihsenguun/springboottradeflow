package com.fatihsengun.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoRegisterUI {

    @NotBlank(message = "The name field cannot be left blank.")
    @Size(min = 2,max = 50)
    private String firstName;

    @NotBlank(message = "The lastname field cannot be left blank.")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank(message = "The email field cannot be left blank.")
    @Email(message = "Please enter a valid email address.")
    private String email;

    @NotBlank(message = "The password field cannot be left blank.")
    @Size(min = 6, max = 20,message = "The password must be at least 6 and no more than 20 characters long.")
    private String password;

}
