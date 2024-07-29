package com.example.jart_cafe.services;

import com.example.jart_cafe.dto.CheckoutRequest;
import com.example.jart_cafe.model.OrderDetails;

import java.util.List;

public interface OrderDetailsService {
    OrderDetails saveOrder(CheckoutRequest checkoutRequest);
    List<CheckoutRequest> findAll();
}
