package com.ecommerce.model.shopping;

import com.ecommerce.helper.enums.OrderStatus;
import com.ecommerce.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long orderId;

    private Double totalPrice;
    private String shippingAddress;
    private String paymentMethod;
    private LocalDateTime purchaseTime;
    private double amountPurchased;
    private OrderStatus status;

    /*relationship*/
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    /*end of relationship*/
}