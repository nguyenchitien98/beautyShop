package com.tien.entity;

public enum OrderStatus {
    PENDING,    // Đơn mới tạo
    PAID,       // Đã thanh toán
    SHIPPING,
    CANCELLED,  // Đã hủy (optional)
    COMPLETED   // Đã giao hàng (optional)
}