package com.ecommerce.repository.shopping;

import com.ecommerce.model.shopping.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Checkout findByOrderOrderId(Long orderId);
}