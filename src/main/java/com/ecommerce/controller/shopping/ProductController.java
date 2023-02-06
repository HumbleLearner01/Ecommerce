package com.ecommerce.controller.shopping;

import com.ecommerce.model.shopping.Product;
import com.ecommerce.service.shopping.ProductService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    //add product
    @PostMapping("")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    //update product
    @PutMapping("")
    public Product update(@RequestBody Product product) {
        return productService.save(product);
    }

    //get single product
    @GetMapping("/{productId}")
    public Product findByProductId(@PathVariable("productId") int productId) {
        return productService.findById(productId);
    }

    //get all products
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllQuizzes() {
        return ResponseEntity.ok(productService.findAll());
    }

    //delete product
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable("productId") int productId) {
        productService.deleteById(productId);
        if (findByProductId(productId) != null)
            return new ResponseEntity<>("Fail", HttpStatus.EXPECTATION_FAILED);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
