package com.ecommerce.helper.payload.shopping;

import com.ecommerce.helper.enums.OrderStatus;
import com.ecommerce.helper.payload.user.UserDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderDto implements Serializable {
    private Long orderId;

    private Double totalPrice;
    private String shippingAddress;
    private String paymentMethod;
    private LocalDateTime purchaseTime;
    private OrderStatus status;

    private UserDto user;
    private ProductDto product;
}