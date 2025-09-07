package com.bootlabs.repository;

import com.bootlabs.entity.Order;
import com.bootlabs.entity.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class OrderFormulaIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldCalculateOrderTotalCorrectly() {
        // Given
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // Create order items
        OrderItem item1 = new OrderItem();
        item1.setOrder(savedOrder);
        item1.setQuantity(2);
        item1.setPrice(new BigDecimal("25.99"));

        OrderItem item2 = new OrderItem();
        item2.setOrder(savedOrder);
        item2.setQuantity(1);
        item2.setPrice(new BigDecimal("15.50"));

        orderItemRepository.saveAll(Arrays.asList(item1, item2));

        entityManager.flush();
        entityManager.clear();

        // When
        Order orderWithTotal = orderRepository.findById(savedOrder.getId()).orElseThrow();

        // Then
        BigDecimal expectedTotal = new BigDecimal("67.48"); // (2 * 25.99) + (1 * 15.50)
        assertThat(orderWithTotal.getTotalAmount()).isEqualByComparingTo(expectedTotal);
        assertThat(orderWithTotal.getItemCount()).isEqualTo(2);
    }

    @Test
    void shouldHandleEmptyOrderItems() {
        // Given
        Order emptyOrder = new Order();
        emptyOrder.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(emptyOrder);

        // ❗ Force context clear
        entityManager.flush();
        entityManager.clear();

        // When
        Order orderWithTotal = orderRepository.findById(savedOrder.getId()).orElseThrow();

        // Then
        assertThat(orderWithTotal.getTotalAmount()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(orderWithTotal.getItemCount()).isEqualTo(0);
    }
}