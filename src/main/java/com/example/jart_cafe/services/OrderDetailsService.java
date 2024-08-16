package com.example.jart_cafe.services;

import com.example.jart_cafe.dto.CheckoutRequestDTO;
import com.example.jart_cafe.dto.CheckoutRequestDetailsDTO;
import com.example.jart_cafe.model.OrderDetails;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {
    Long saveOrder(CheckoutRequestDTO checkoutRequestDTO);

    void updateTransaction(Long orderId);

    void updateReviewStatus(Long orderId, Long artworkId, boolean status);

    List<CheckoutRequestDetailsDTO> findAll();

    Optional<OrderDetails> findOrderById(Long id);

    void deleteOrder(Long id);

    List<OrderDetails> getOrdersByCustomerEmail(String email);

    void updateOrderStatus(Long orderId, Boolean newStatus);

    void updateOrderDate(Long orderId, Date newCompletedDate);


}
