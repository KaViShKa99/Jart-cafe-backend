package com.example.jart_cafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artwork_id")
    private Long artworkId;
    @Column(name = "artwork_type")
    private String type;
    @Column(name = "artwork_material")
    private String material;
    @Column(name = "artwork_price")
    private Double price;

//    @Column(name = "artwork_imageUrl")
//    private String imageUrl;



}

