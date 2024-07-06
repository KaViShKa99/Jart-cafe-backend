package com.example.jart_cafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="material_id")
    private Long id;
    @Column(name="material_name")
    private String material;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SizePrice> sizes;

}
