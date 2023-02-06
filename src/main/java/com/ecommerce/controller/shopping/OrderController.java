package com.ecommerce.controller.shopping;

import com.ecommerce.helper.enums.OrderStatus;
import com.ecommerce.helper.payload.OrderResponse;
import com.ecommerce.service.shopping.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    //cancel order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(new OrderResponse("Order has been cancelled successfully",OrderStatus.CANCELLED));
    }
}