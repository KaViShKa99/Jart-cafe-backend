package com.example.jart_cafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name",nullable = false,unique = false)
    private String name;
    @Column(name = "user_email",nullable = false,unique = true)
    private String email;
    @Column(name = "user_password",nullable = false)
    private String password;

}
