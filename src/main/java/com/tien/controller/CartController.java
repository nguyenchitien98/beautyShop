package com.tien.controller;

import com.tien.entity.CartItem;
import com.tien.entity.Product;
import com.tien.entity.User;
import com.tien.payload.ApiCode;
import com.tien.payload.ApiResponseBuilder;
import com.tien.service.CartItemService;
import com.tien.service.ProductService;
import com.tien.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cartService.addToCart(user, product, quantity);
        return ResponseEntity.ok(ApiResponseBuilder.success(cartItem));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserCart(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<CartItem> cartItems = cartService.getUserCart(user);
        return ResponseEntity.ok(ApiResponseBuilder.success(cartItems));
    }

}
