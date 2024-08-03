package com.example.jart_cafe.dto;

import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Data
public class CheckoutRequestDTO {

    private Boolean orderTransaction;
    private Boolean orderStatus;
    private Date orderedDate;
    private Date completedDate;
    private String customerName;
    private String customerEmail;
    private List<PurchaseItemDTO> items;

}
