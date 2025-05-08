package com.tien.controller;

import com.tien.entity.OrderStatus;
import com.tien.payload.ApiResponseBuilder;
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
    public ResponseEntity<?> confirmShipping(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.SHIPPING);
        return ResponseEntity.ok(ApiResponseBuilder.success("Đã xác nhận giao hàng"));
//        return ResponseEntity.ok("Đã xác nhận giao hàng");
    }

    @PutMapping("/{orderId}/confirm-completed")
    public ResponseEntity<?> confirmCompleted(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.COMPLETED);

        return ResponseEntity.ok(ApiResponseBuilder.success("Đã xác nhận hoàn tất đơn hàng"));
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED);
        // TODO: Xử lý thêm refund nếu đã thanh toán

        return ResponseEntity.ok(ApiResponseBuilder.success("Đã hủy đơn hàng và hoàn tiền nếu cần"));
    }
}
