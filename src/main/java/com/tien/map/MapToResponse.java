package com.tien.map;

import com.tien.dto.response.CategoryResponse;
import com.tien.dto.response.ProductResponse;
import com.tien.dto.response.RatingResponse;
import com.tien.entity.Category;
import com.tien.entity.Product;
import com.tien.entity.Rating;
import org.springframework.stereotype.Component;

@Component
public class MapToResponse {
    public ProductResponse mapToProductResponse(Product product) {
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

    public CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse
                .builder().id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public RatingResponse convertToRatingResponse(Rating r) {
        return RatingResponse.builder()
                .id(r.getId())
                .rating(r.getRating())
                .comment(r.getComment())
                .userFullName(r.getUser().getFullName())
                .avatar(r.getUser().getAvatar())
                .createdAt(r.getCreatedAt())
                .build();
    }
}
