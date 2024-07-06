package com.example.jart_cafe.dto;

import com.example.jart_cafe.model.Material;
import lombok.Data;

import java.util.List;

@Data
public class ArtworkDTO {

    private String category;
    private String description;
    private String title;
    private Double price;
    private Double lastPrice;
    private List<String> images;
    private List<MaterialDTO> materials;

}
