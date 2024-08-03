package com.example.jart_cafe.repositories;

import com.example.jart_cafe.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Long> {
    List<OrderDetails> findByCustomerEmail(String customerEmail);
    List<OrderDetails> findByOrderTransactionTrue();
    List<OrderDetails> findByCustomerEmailAndOrderTransactionTrue(String email);


}
