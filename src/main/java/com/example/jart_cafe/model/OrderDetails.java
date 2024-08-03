package com.example.jart_cafe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Boolean orderTransaction;
    private Date orderedDate;
    private Date completedDate;
    private Boolean orderStatus;
    private String customerName;
    private String customerEmail;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PurchaseItem> items;
}
