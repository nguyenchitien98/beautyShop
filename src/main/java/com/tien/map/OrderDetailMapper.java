package com.tien.map;

import com.tien.dto.response.OrderDetailResponse;
import com.tien.entity.OrderDetail;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailMapper {
    public static OrderDetailResponse toResponse(OrderDetail od) {
        return OrderDetailResponse.builder()
                .id(od.getId())
                .quantity(od.getQuantity())
                .price(od.getPrice())
                .productId(od.getProduct().getId())
                .productName(od.getProduct().getName())
                .build();
    }

    public static List<OrderDetailResponse> toResponseList(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderDetailMapper::toResponse)
                .collect(Collectors.toList());
    }
}
