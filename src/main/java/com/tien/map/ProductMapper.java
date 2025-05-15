package com.tien.map;

import com.tien.dto.request.ProductRequest;
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
                .discountPrice(product.getDiscountPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .brand(product.getBrand())
                .origin(product.getOrigin())
                .skinType(product.getSkinType())
                .weightOrVolume(product.getWeightOrVolume())
//                .usageInstructions(product.getUsageInstructions()) // Mấy đoạn này sẽ nâng cấp sau
//                .ingredients(product.getIngredients()) // Mấy đoạn này sẽ nâng cấp sau
//                .isAvailable(product.getIsAvailable()) // Mấy đoạn này sẽ nâng cấp sau
                .isFeatured(product.getIsFeatured())
//                .videoUrl(product.getVideoUrl()) // Mấy đoạn này sẽ nâng cấp sau
                .tags(product.getTags())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .averageRating(product.getAverageRating())
                .build();
    }

    public static List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ✅ Chuyển từ DTO -> entity
    public static Product toEntity(ProductRequest dto) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .discountPrice(dto.getDiscountPrice())
                .stockQuantity(dto.getStockQuantity())
                .imageUrl(dto.getImageUrl())
                .brand(dto.getBrand())
                .origin(dto.getOrigin())
                .skinType(dto.getSkinType())
                .weightOrVolume(dto.getWeightOrVolume())
//                .usageInstructions(dto.getUsageInstructions()) // Mấy đoạn này sẽ nâng cấp sau
//                .ingredients(dto.getIngredients()) // Mấy đoạn này sẽ nâng cấp sau
//                .isAvailable(dto.getIsAvailable()) // Mấy đoạn này sẽ nâng cấp sau
                .isFeatured(dto.getIsFeatured())
//                .videoUrl(dto.getVideoUrl()) // Mấy đoạn này sẽ nâng cấp sau
                .tags(dto.getTags())
                // Chưa set category (vì cần lấy từ DB)
                .build();
    }
}
