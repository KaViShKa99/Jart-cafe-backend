package com.example.jart_cafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Artwork {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "artwork_id")
//    private Long artworkId;
//    @Column(name = "artwork_type")
//    private String type;
//    @Column(name = "artwork_material")
//    private String material;
//    @Column(name = "artwork_price")
//    private Double price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String title;
    private Double price;
    private Double lastPrice;

    @ElementCollection
    private List<String> images;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Material> materials;





}

