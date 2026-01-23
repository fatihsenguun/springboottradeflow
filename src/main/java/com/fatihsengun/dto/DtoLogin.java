package com.fatihsengun.dto;

import com.fatihsengun.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoLogin  {
    public String accessToken;

    public String refreshToken;

    public String firstName;

    public String lastName;

    public RoleType role;



}
