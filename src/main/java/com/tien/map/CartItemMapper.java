package com.tien.map;

import com.tien.dto.response.CartItemResponse;
import com.tien.entity.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemMapper {
    public static CartItemResponse toResponse(CartItem item) {
        return CartItemResponse.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .userId(item.getUser().getId())
                .username(item.getUser().getUsername())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .build();
    }

    public static List<CartItemResponse> toResponseList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemMapper::toResponse)
                .collect(Collectors.toList());
    }
}
