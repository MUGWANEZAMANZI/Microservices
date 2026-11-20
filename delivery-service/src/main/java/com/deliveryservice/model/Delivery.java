package com.deliveryservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long customerId;
    private String deliveryAddress;
    private String status;
}
