package com.example.jart_cafe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SizePriceDTO {
    @JsonProperty("design")
    private String design;

    @JsonProperty("price")
    private Double price;
}
