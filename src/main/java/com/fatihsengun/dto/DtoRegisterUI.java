package com.fatihsengun.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoRegisterUI {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String fullName;

    private String role;
}
