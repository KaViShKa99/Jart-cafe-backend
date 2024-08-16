package com.example.jart_cafe.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseItemDTO {
    private Long artworkId;
    private String category;
    private String designerNote;
    private double eachPrice;
    private String figure;
    private boolean physicalArt;
    private String materialAndSize;
    private String numOfPersons;
    private double price;
    private Long quantity;
    private List<String> productImage;
    private String style;
    private double total;
//    private String uploadedImage;
    private List<String> uploadedImage;
    private boolean reviewStatus;
}
