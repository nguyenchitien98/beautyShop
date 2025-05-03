package com.tien.service;

import com.tien.entity.Order;
import com.tien.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    void deleteOrder(Long id);
    Order placeOrder(User user);
    List<Order> getUserOrders(User user);
}
