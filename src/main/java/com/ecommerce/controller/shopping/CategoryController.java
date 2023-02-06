package com.ecommerce.controller.shopping;

import com.ecommerce.model.shopping.Category;
import com.ecommerce.service.shopping.CategoryService;
import com.ecommerce.helper.payload.ApiResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    //show all category
    @GetMapping("")
    public ResponseEntity<List<Category>> findAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    //create a category
    @PostMapping("")
    public ResponseEntity<ApiResponse> save(@RequestBody Category category) {
        categoryService.save(category);
        return new ResponseEntity<>(new ApiResponse("Category created", true), HttpStatus.CREATED);
    }

    //update a category
    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> update(@PathVariable int categoryId, @RequestBody Category category) {
        if (!categoryService.findById(categoryId))
            return new ResponseEntity<>(new ApiResponse("couldn't find category with '"+categoryId+"'", false), HttpStatus.NOT_FOUND);
        else {
            categoryService.saveUpdate(categoryId, category);
            return new ResponseEntity<>(new ApiResponse("update success", true), HttpStatus.OK);
        }
    }

    //my own update implementation
    @PutMapping("")
    public ResponseEntity<Category> update(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.saveUpdate(category), HttpStatus.CREATED);
    }
}
