package com.tien.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long id;
    private Integer quantity;
    private Double price;
    private Long productId;
    private String productName;
}
