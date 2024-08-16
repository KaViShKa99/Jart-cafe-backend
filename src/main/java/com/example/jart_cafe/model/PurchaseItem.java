package com.example.jart_cafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_item_id")
    private Long id;
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
    @ElementCollection
    private List<String> productImage;
    private String style;
    private double total;
    @ElementCollection
    private List<String> uploadedImage;
    private boolean reviewStatus;
//    private String uploadedImage;


}
