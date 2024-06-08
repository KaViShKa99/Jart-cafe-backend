package com.example.jart_cafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SizePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sizes_id")
    private Long sizeId;
    @Column(name = "artwork_id")
    private Long artworkId;
    @Column(name = "sizes_design")
    private String design;
    @Column(name = "sizes_price")
    private Double price;

}
