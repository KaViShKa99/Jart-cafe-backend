package com.example.jart_cafe.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class CheckoutRequest {

    private String customerName;
    private String customerEmail;
    private List<PurchaseItemDTO> items;

}
