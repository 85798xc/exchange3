package com.example.exchange.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "supported_currencies")
@Data
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency",nullable = false, unique = true)
    private String currency;

}
