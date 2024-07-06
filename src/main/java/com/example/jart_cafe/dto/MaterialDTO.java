package com.example.jart_cafe.dto;

import com.example.jart_cafe.model.SizePrice;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class MaterialDTO {

    @JsonProperty("material")
    private String material;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SizePriceDTO> sizes;
}
