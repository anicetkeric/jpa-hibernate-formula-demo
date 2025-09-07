package com.bootlabs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // Calculate total from order_items table
    @Formula("(SELECT COALESCE(SUM(oi.quantity * oi.price), 0) " +
            "FROM order_items oi WHERE oi.order_id = id)")
    private BigDecimal totalAmount;

    // Count of items in the order
    @Formula("(SELECT COUNT(*) FROM order_items oi WHERE oi.order_id = id)")
    private Integer itemCount;

    // Average item price
    @Formula("(SELECT COALESCE(AVG(oi.price), 0) " +
            "FROM order_items oi WHERE oi.order_id = id)")
    private BigDecimal averageItemPrice;
}