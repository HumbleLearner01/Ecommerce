package com.ecommerce.repository.shopping;

import com.ecommerce.model.shopping.Cart;
import com.ecommerce.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserOrderByCreatedAtDesc(User user);

    Optional<Cart> findAllByUserUserIdOrderByCreatedAtDesc(Long userId);
}