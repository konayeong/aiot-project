package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.exception.OrderSaveException;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    OrderService orderService = new OrderServiceImpl(orderRepository);

    @Test
    @DisplayName("주문 내역 저장 Service 테스트")
    void testSaveOrderService() {
        Order order = new Order("testUser", 1, 10000);

        when(orderRepository.save(any(Order.class))).thenReturn(1);

        orderService.saveOrder(order);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 내역 저장 Service 테스트 - 실패")
    void testSaveOrderService_Fail() {
        Order order = new Order("testUSer", 1, 10000);

        when(orderRepository.save(any(Order.class))).thenReturn(0);

        assertThrows(OrderSaveException.class, () -> orderService.saveOrder(order));
    }

    @Test
    @DisplayName("주문 내역 페이징 조회")
    void testGetOrderPage() {
        String userId = "testUser";
        int page = 1;
        int pageSize = 10;

        Page<Order> mockPage = new Page<>(Collections.emptyList(), 0);
        when(orderRepository.findAllByUserId(userId, page, pageSize)).thenReturn(mockPage);

        Page<Order> resultPage = orderService.getOrderPage(userId, page, pageSize);

        assertNotNull(resultPage);
        assertEquals(mockPage, resultPage);
        verify(orderRepository, times(1)).findAllByUserId(userId, page, pageSize);
    }
}
