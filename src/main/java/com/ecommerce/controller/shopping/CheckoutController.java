package com.ecommerce.controller.shopping;

import com.ecommerce.helper.payload.shopping.CheckoutDto;
import com.ecommerce.service.shopping.CheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/checkout")
@AllArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;

    //purchase order
    @PostMapping
    public ResponseEntity<CheckoutDto> purchase(@Valid @RequestBody CheckoutDto checkoutDto) {
        return new ResponseEntity<>(checkoutService.purchaseOrder(checkoutDto), HttpStatus.CREATED);
    }
}