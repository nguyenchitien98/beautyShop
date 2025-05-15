package com.tien.map;

import com.tien.dto.response.OrderResponse;
import com.tien.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .userId(order.getUser().getId())
                .username(order.getUser().getUsername())
                .orderDetails(order.getOrderDetails().stream()
                        .map(OrderDetailMapper::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public static List<OrderResponse> toResponseList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }
}
