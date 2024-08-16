package com.example.jart_cafe.api;


import com.example.jart_cafe.dto.CheckoutRequestDTO;
import com.example.jart_cafe.dto.CheckoutRequestDetailsDTO;
import com.example.jart_cafe.model.OrderDetails;
import com.example.jart_cafe.services.OrderDetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping
    public List<CheckoutRequestDetailsDTO> getAllOrders(){
        return orderDetailsService.findAll();
    }
    @PutMapping("update/status/{id}")
    public ResponseEntity<String> updatedEntity(@PathVariable Long id, @RequestBody Boolean status){
        try {
            System.out.println(status);
            orderDetailsService.updateOrderStatus(id, status);
            return ResponseEntity.ok("Order status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found or error updating status.");
        }
    }
    @PutMapping("update/complete-date/{id}")
    public ResponseEntity<String> updatedEntity(@PathVariable Long id, @RequestBody Date date){
        try {
            orderDetailsService.updateOrderDate(id, date);
            return ResponseEntity.ok("Order date updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found or error updating date.");
        }
    }
    @GetMapping("get/{email}")
    public List<OrderDetails> getOrdersByEmail(@PathVariable String email) {
        return orderDetailsService.getOrdersByCustomerEmail(email);
    }

    @PutMapping("/update/review-status/{orderId}/{purchaseId}")
    public ResponseEntity<String> updateReviewStatus(
            @PathVariable Long orderId,
            @PathVariable Long purchaseId,
            @RequestBody Boolean status) {

        try {
            orderDetailsService.updateReviewStatus(orderId, purchaseId, status);

            return ResponseEntity.ok("Review status updated successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order or item not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the review status.");
        }
    }

}
