package com.example.jart_cafe.dto;

import com.example.jart_cafe.model.Image;
import com.example.jart_cafe.model.SizePrice;
import lombok.Data;

import java.util.List;

@Data
public class ArtworkDetailsDTO {
    private Long artworkId;
    private String material;
    private String type;
    private Double price;
    private List<SizePrice> sizePrices;
    private List<Image> images;
}
