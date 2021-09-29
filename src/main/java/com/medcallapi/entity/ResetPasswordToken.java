package com.medcallapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Entity @Table(name = "reset_password_token")
public class ResetPasswordToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    public ResetPasswordToken(UserEntity user) {
        this.user = user;
        this.createdAt = LocalDate.now();
        this.resetPasswordToken = UUID.randomUUID().toString();
    }
}
