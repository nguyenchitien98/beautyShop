package com.tien.service;

import com.tien.dto.request.CategoryRequest;
import com.tien.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest category);
    List<Category> getAllCategories();
}
