package com.tien.service;

import com.tien.entity.CartItem;
import com.tien.entity.Product;
import com.tien.entity.User;

import java.util.List;

public interface CartItemService {
    CartItem createCartItem(CartItem cartItem);
    CartItem getCartItemById(Long id);
    List<CartItem> getAllCartItems();
    void deleteCartItem(Long id);
    CartItem addToCart(User user, Product product, Integer quantity);
    List<CartItem> getUserCart(User user);
    void clearCart(User user);
}
