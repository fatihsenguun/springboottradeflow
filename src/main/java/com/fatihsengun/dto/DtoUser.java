package com.fatihsengun.dto;

import com.fatihsengun.entity.BaseEntity;
import com.fatihsengun.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser extends BaseEntity {
    private String firstName;

    private String lastName;

    private String email;

    private RoleType role;

    private DtoWallet wallet;
}
