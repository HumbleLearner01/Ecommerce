package com.ecommerce.model.shopping;

import com.ecommerce.helper.enums.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkoutId;

    private String shippingAddress;
    private String paymentMethod;
    private double totalCost;
    private String cardNumber;
    private String cvv2;
    private OrderStatus orderStatus;

    @CreationTimestamp
    private Date purchaseDate;

    /*relationship*/
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;
    /*end of relationship*/
}