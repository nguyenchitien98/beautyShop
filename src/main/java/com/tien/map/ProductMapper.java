package com.tien.map;

import com.tien.dto.response.ProductResponse;
import com.tien.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

//Tạo DTO rõ ràng và tối ưu cho Product và Rating:
//ProductDTO: không trả về full Category, chỉ tên danh mục hoặc ID.
//RatingDTO: không trả về full User, chỉ tên người dùng hoặc ID.
//Tránh vòng lặp Product → Rating → Product.

public class ProductMapper {
    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .ratings(product.getRatings().stream()
                        .map(RatingMapper::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public static List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }
}
