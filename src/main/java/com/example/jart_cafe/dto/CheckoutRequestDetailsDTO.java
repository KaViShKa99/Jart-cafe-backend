package com.example.jart_cafe.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CheckoutRequestDetailsDTO {

    private Long orderId;
    private Boolean orderStatus;
    private Date orderedDate;
    private Date completedDate;
    private String customerName;
    private String customerEmail;
    private List<PurchaseItemDTO> items;
}
