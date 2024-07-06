package com.example.jart_cafe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pwd_reset_id")
    private Long id;
    @Column(name = "pwd_reset_token")
    private String token;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn( name = "user_id")
    private User user;

    @Column(name = "pwd_expire_date")
    private Date expiryDate;
}
