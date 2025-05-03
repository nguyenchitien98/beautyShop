package com.tien.controller;

import com.tien.entity.CartItem;
import com.tien.entity.Product;
import com.tien.entity.User;
import com.tien.service.CartItemService;
import com.tien.service.ProductService;
import com.tien.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/add")
    public CartItem addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);
        return cartService.addToCart(user, product, quantity);
    }

    @GetMapping("/{userId}")
    public List<CartItem> getUserCart(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return cartService.getUserCart(user);
    }

}
