package com.tien.controller;

import com.tien.entity.Order;
import com.tien.entity.User;
import com.tien.service.OrderService;
import com.tien.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/place/{userId}")
    public Order placeOrder(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return orderService.placeOrder(user);
    }

    @GetMapping("/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return orderService.getUserOrders(user);
    }
}
