package com.tien.service.impl;

import com.tien.entity.CartItem;
import com.tien.entity.Product;
import com.tien.entity.User;
import com.tien.repository.CartItemRepository;
import com.tien.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException("CartItem not found"));
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItem addToCart(User user, Product product, Integer quantity) {
        CartItem cartItem = CartItem.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .build();
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getUserCart(User user) {
        return cartItemRepository.findByUser(user);
    }

    @Override
    public void clearCart(User user) {
        cartItemRepository.deleteByUser(user);
    }
}
