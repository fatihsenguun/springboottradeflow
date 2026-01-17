package com.fatihsengun.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    @ManyToOne
    private User user;
}
