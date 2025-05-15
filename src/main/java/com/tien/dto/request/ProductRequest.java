package com.tien.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotNull(message = "Giá sản phẩm là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private Double price;

    @DecimalMin(value = "0.0", message = "Giá khuyến mãi không được âm")
    private Double discountPrice;

    @NotNull(message = "Số lượng tồn kho là bắt buộc")
    @Min(value = 0, message = "Số lượng không được âm")
    private Integer stockQuantity;

    @NotBlank(message = "Ảnh sản phẩm là bắt buộc")
    private String imageUrl;
    private String brand;
    private String origin;
    private String skinType;
    private String weightOrVolume;
    private Boolean isFeatured;
    private List<String> tags;

    @NotNull(message = "CategoryId is required")
    private Long categoryId;
}