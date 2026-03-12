package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;

public interface OrderRepository {
    int save(Order order);
    Page<Order> findAllByUserId(String userId, int page, int pageSize);
    long totalCountByUserId(String userId);
}
