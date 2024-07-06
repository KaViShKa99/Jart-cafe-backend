package com.example.jart_cafe.dto;

import com.example.jart_cafe.model.Image;
import com.example.jart_cafe.model.SizePrice;
import lombok.Data;

import java.util.List;

@Data
public class ArtworkDetailsDTO {
    private Long artworkId;
    private String category;
    private String description;
    private String title;
    private Double price;
    private Double lastPrice;
    private List<String> images;
    private List<MaterialDTO> materials;
}
