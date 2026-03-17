package com.nhnacademy.shoppingmall.order.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.order.exception.OrderSaveException;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void saveOrder(Order order) {
        int result = orderRepository.save(order);
        if(result < 1) {
            throw new OrderSaveException(order.getUserId());
        }
    }

    @Override
    public Page<Order> getOrderPage(String userId, int page, int pageSize) {
        return orderRepository.findAllByUserId(userId, page, pageSize);
    }

    @Override
    public List<OrderItem> getOrderDetails(int orderId) {
        return orderRepository.findOrderDetailsByOrderId(orderId);
    }
}
