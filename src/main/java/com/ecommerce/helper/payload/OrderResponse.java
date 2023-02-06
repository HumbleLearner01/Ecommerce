package com.ecommerce.helper.payload;

import com.ecommerce.helper.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderResponse {
    private String message;
    private OrderStatus orderStatus;
}