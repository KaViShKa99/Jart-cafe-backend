package com.example.jart_cafe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long artworkId;

    private int rating;

    @Column(length = 500)
    private String reviewText;

    private String username;

    private String userEmail;

    private Date reviewedDate;
}
