package com.tien.controller;

import com.tien.dto.response.OrderResponse;
import com.tien.security.model.CustomUserDetails;
import com.tien.entity.Order;
import com.tien.entity.OrderStatus;
import com.tien.entity.User;
import com.tien.service.OrderService;
import com.tien.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable Long id) {
        orderService.payOrder(id);
        return ResponseEntity.ok("Order payment successful");
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok("Order cancelled successfully");
    }

    @PostMapping("/{id}/ship")
    public ResponseEntity<?> shipOrder(@PathVariable Long id) {
        orderService.shipOrder(id);
        return ResponseEntity.ok("Order is now shipping");
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return ResponseEntity.ok("Order has been completed");
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestParam(name = "status", required = false) String status) {
        if (status == null) {
            return ResponseEntity.ok(orderService.getAllOrders());
        }

        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            List<Order> orders = orderService.getOrdersByStatus(orderStatus);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<OrderResponse> myOrders = orderService.getOrdersByUserId(userDetails.getId());
        return ResponseEntity.ok(myOrders);
    }
}
