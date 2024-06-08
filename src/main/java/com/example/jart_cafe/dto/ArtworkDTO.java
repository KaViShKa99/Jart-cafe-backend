package com.example.jart_cafe.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArtworkDTO {
    private String type;
    private String material;
    private Double price;
    private List<String> imageUrl;
    private List<SizePriceDTO> size;
}
