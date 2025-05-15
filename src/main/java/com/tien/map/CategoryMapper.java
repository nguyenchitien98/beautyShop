package com.tien.map;

import com.tien.dto.response.CategoryResponse;
import com.tien.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .products(category.getProducts().stream()
                        .map(ProductMapper::toResponse)
                        .collect(Collectors.toList())).build();
    }

    public static List<CategoryResponse> toResponseList(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
