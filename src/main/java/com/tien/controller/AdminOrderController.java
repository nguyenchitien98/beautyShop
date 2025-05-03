package com.tien.controller;

import com.tien.entity.OrderStatus;
import com.tien.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @PutMapping("/{orderId}/confirm-shipping")
    public ResponseEntity<String> confirmShipping(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.SHIPPING);
        return ResponseEntity.ok("Đã xác nhận giao hàng");
    }

    @PutMapping("/{orderId}/confirm-completed")
    public ResponseEntity<String> confirmCompleted(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.COMPLETED);
        return ResponseEntity.ok("Đã xác nhận hoàn tất đơn hàng");
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED);
        // TODO: Xử lý thêm refund nếu đã thanh toán
        return ResponseEntity.ok("Đã hủy đơn hàng và hoàn tiền nếu cần");
    }
}
