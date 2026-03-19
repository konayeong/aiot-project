package com.nhnacademy.shoppingmall.order.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.order.exception.OrderSaveException;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void saveOrder(Order order) {
        /**
         * 주문 요청
         * 재고 및 잔고 확인 -> rollback
         * (검증통과)
         * 주문 처리
         * - order, orderItem 생성
         * - 상품 재고 차감
         * - 회원 포인트 차감
         * - 주문 포인트 적립
         */

        int result = orderRepository.save(order);
        if(result < 1) {
            throw new OrderSaveException(order.getUserId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getOrderPage(String userId, int page, int pageSize) {
        return orderRepository.findAllByUserId(userId, page, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> getOrderDetails(int orderId) {
        return orderRepository.findOrderDetailsByOrderId(orderId);
    }
}
