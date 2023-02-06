package com.ecommerce.controller.shopping;

import com.ecommerce.helper.payload.ApiResponse;
import com.ecommerce.helper.payload.shopping.CartDto;
import com.ecommerce.service.shopping.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    //add to cart
    @PostMapping
    public ResponseEntity<ApiResponse> addToCart(@RequestBody CartDto cartDto) {
        cartService.save(cartDto);
        return new ResponseEntity<>(new ApiResponse("Product added to cart successfully!",true), HttpStatus.CREATED);
    }

    //delete from cart
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteItemFromCart(@PathVariable Long cartItemId) {
        cartService.delete(cartItemId);
        return new ResponseEntity<>(new ApiResponse("Item deleted from cart successfully!",true),HttpStatus.OK);
    }

    //get all items from cart
    @GetMapping
    public ResponseEntity<List<CartDto>> getAllItemsOfCart() {
        return ResponseEntity.ok(cartService.findAll());
    }

    //update items in the cart i.e. update the quantity
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartDto> updateItemOfCart(@RequestBody Map<String, Integer> updatePayload, @PathVariable Long cartItemId) {
        return new ResponseEntity<>(cartService.save(updatePayload,cartItemId),HttpStatus.OK);
    }
}