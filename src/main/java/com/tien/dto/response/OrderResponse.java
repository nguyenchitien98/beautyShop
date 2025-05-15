package com.tien.dto.response;

import com.tien.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Double totalAmount;
    private OrderStatus status;
    private Long userId;
    private String username;
    private List<OrderDetailResponse> orderDetails;
    private LocalDateTime createdAt;
}
