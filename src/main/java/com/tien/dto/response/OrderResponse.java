package com.tien.dto.response;

import com.tien.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Double totalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
