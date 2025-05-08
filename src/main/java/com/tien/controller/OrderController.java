package com.tien.controller;

import com.tien.dto.response.OrderResponse;
import com.tien.payload.ApiResponseBuilder;
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
    public ResponseEntity<?> placeOrder(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Order order = orderService.placeOrder(user);
        return ResponseEntity.ok(ApiResponseBuilder.success(order));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<Order> orders = orderService.getUserOrders(user);
        return ResponseEntity.ok(ApiResponseBuilder.success(orders));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable Long id) {
        orderService.payOrder(id);
        return ResponseEntity.ok(ApiResponseBuilder.success("Order payment successful"));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponseBuilder.success("Order cancelled successfully"));
//        return ResponseEntity.ok("Order cancelled successfully");
    }

    @PostMapping("/{id}/ship")
    public ResponseEntity<?> shipOrder(@PathVariable Long id) {
        orderService.shipOrder(id);
        return ResponseEntity.ok(ApiResponseBuilder.success("Order is now shipping"));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return ResponseEntity.ok(ApiResponseBuilder.success("Order has been completed"));
    }

    @GetMapping
    public ResponseEntity<?> getOrdersByStatus(@RequestParam(name = "status", required = false) String status) {
        if (status == null) {
            return ResponseEntity.ok(orderService.getAllOrders());
        }

        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            List<Order> orders = orderService.getOrdersByStatus(orderStatus);
            return ResponseEntity.ok(ApiResponseBuilder.success(orders));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<OrderResponse> myOrders = orderService.getOrdersByUserId(userDetails.getId());
        return ResponseEntity.ok(ApiResponseBuilder.success(myOrders));
    }
}
