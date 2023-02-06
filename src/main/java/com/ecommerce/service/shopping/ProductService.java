package com.ecommerce.service.shopping;

import com.ecommerce.helper.exception.ResourceNotFoundException;
import com.ecommerce.model.shopping.Product;
import com.ecommerce.repository.shopping.ProductRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ProductService {
    private final ProductRepository productRepo;

    //add category
    public Product save(Product product) {
        return productRepo.save(product);
    }

    //update product
    public Product saveUpdate(Product product) {
        return productRepo.save(product);
    }

    //get all product
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    //get single product
    public Product findById(int productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductID", String.valueOf(productId)));
    }

    //delete product
    public void deleteById(int productId) {
        productRepo.deleteById(productId);
    }
}