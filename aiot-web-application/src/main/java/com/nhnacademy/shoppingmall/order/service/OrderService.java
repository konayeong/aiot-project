package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderItem;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order);
    Page<Order> getOrderPage(String userId, int page, int pageSize);
    List<OrderItem> getOrderDetails(int orderId);
}
