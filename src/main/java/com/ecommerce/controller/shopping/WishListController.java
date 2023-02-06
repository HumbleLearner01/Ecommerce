package com.ecommerce.controller.shopping;

import com.ecommerce.service.shopping.WishListService;
import com.ecommerce.helper.payload.ApiResponse;
import com.ecommerce.helper.payload.shopping.WishListDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
@AllArgsConstructor
public class WishListController {
    private final WishListService wishListService;

    //add a product in wishlist
    @PostMapping
    public ResponseEntity<WishListDto> save(@RequestBody WishListDto wishListDto) {
        return new ResponseEntity<>(wishListService.save(wishListDto.getProductId()), HttpStatus.CREATED);
    }

    //delete a product in wishlist
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        wishListService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Product removed from wishlist successfully!",true));
    }

    //get all the products in wishlist
    @GetMapping
    public ResponseEntity<List<WishListDto>> findAll() {
        return ResponseEntity.ok(wishListService.findAll());
    }
}