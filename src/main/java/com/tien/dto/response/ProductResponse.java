package com.tien.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double discountPrice;
    private Integer stockQuantity;
    private String imageUrl;
    private String brand;
    private String origin;
    private String skinType;
    private String weightOrVolume;
//    private String usageInstructions;
//    private String ingredients;
//    private Boolean isAvailable;
    private Boolean isFeatured;
//    private String videoUrl;
    private List<String> tags;
    private Long categoryId;
    private String categoryName;
    private Double averageRating;
}
