package com.tien.map;

import com.tien.dto.response.CategoryResponse;
import com.tien.dto.response.ProductResponse;
import com.tien.entity.Category;
import com.tien.entity.Product;

public class MapToResponse {
    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }

    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse
                .builder().id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
