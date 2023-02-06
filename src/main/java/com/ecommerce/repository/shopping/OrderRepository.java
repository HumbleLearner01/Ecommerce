package com.ecommerce.repository.shopping;

import com.ecommerce.helper.enums.OrderStatus;
import com.ecommerce.model.shopping.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsOrderByUserUserId(Long userId);
    Optional<Order> findByStatusAndOrderId(OrderStatus status, Long orderId);
}