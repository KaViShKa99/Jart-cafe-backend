package com.example.jart_cafe.repositories;

import com.example.jart_cafe.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermanentOrderRepository extends JpaRepository<OrderDetails,Long> {
}
