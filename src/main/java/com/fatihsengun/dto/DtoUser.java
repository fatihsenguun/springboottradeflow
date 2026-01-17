package com.fatihsengun.dto;

import com.fatihsengun.entity.BaseEntity;
import com.fatihsengun.enums.RoleType;
import lombok.Data;

@Data
public class DtoUser extends BaseEntity {
    private String firstName;

    private String lastName;

    private String email;

    private RoleType role;

    private DtoWallet wallet;
}
