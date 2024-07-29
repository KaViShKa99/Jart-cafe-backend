package com.example.jart_cafe.api;


import com.example.jart_cafe.dto.CheckoutRequest;
import com.example.jart_cafe.model.OrderDetails;
import com.example.jart_cafe.model.PurchaseItem;
import com.example.jart_cafe.services.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping
    public List<CheckoutRequest> getAllOrders(){
//        System.out.println("orderDetailsService.findAll()");
        return orderDetailsService.findAll();
//        return null;
    }
}
