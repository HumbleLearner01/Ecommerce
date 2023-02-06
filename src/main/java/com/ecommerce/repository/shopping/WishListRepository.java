package com.ecommerce.repository.shopping;

import com.ecommerce.model.shopping.WishList;
import com.ecommerce.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findByUserId(Long userId);
    List<WishList> findAllByUserOrderByCreatedAtDesc(User user);
}