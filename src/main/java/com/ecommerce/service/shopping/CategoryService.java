package com.ecommerce.service.shopping;

import com.ecommerce.model.shopping.Category;
import com.ecommerce.repository.shopping.CategoryRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class CategoryService {
    private final CategoryRepository categoryRepo;

    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    public void saveUpdate(int categoryId, Category updateCategory) {
        Category category = categoryRepo.findById(categoryId).get();
        category.setCategoryName(updateCategory.getCategoryName());
        category.setDescription(updateCategory.getDescription());
        category.setImageUrl(updateCategory.getImageUrl());
        categoryRepo.save(category);
    }

    //my own update implementation
    public Category saveUpdate(Category category) {
        return categoryRepo.save(category);
    }

    public boolean findById(int categoryId) {
        return categoryRepo.findById(categoryId).isPresent();
    }
}