package com.example.jart_cafe.dto;

import com.example.jart_cafe.model.SizePrice;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class SizePriceDTO {

    @JsonProperty("size")
    private String size;

    @JsonProperty("price")
    private Double price;

}
