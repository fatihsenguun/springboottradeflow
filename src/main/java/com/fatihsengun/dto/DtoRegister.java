package com.fatihsengun.dto;

import com.fatihsengun.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class DtoRegister {

    private UUID id;

    private String email;

    private String password ;

    private String fullName;

    private RoleType role;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
