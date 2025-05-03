package com.tien.service;

import com.tien.dto.response.OrderResponse;
import com.tien.entity.Order;
import com.tien.entity.OrderStatus;
import com.tien.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    void deleteOrder(Long id);
    Order placeOrder(User user);
    List<Order> getUserOrders(User user);
    void payOrder(Long orderId);
    void cancelOrder(Long orderId);
    void shipOrder(Long orderId);
    void completeOrder(Long orderId);
    List<Order> getOrdersByStatus(OrderStatus status);
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
    List<OrderResponse> getOrdersByUserId(Long userId);
}
