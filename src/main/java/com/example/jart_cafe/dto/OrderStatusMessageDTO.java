package com.example.jart_cafe.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class OrderStatusMessageDTO {
    private Long orderId;
    private Boolean orderStatus;


}
