package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;

public interface OrderService {
    void saveOrder(Order order);
    Page<Order> getOrderPage(String userId, int page, int pageSize);
}
