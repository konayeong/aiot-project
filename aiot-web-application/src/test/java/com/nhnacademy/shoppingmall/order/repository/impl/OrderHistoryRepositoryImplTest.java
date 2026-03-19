package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.AddressRepository;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class OrderHistoryRepositoryImplTest {
    OrderRepository orderRepository = new OrderRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    AddressRepository addressRepository = new AddressRepositoryImpl();

    User testUser;
    int testAddress = 1;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();

        testUser = new User("testUser", "testUser", "testUser", "20000000", User.Auth.ROLE_USER, 100_0000, LocalDateTime.now(), null);

        userRepository.save(testUser);
    }
    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("주문 저장 테스트")
    void testSave() {
        Order order = new Order(testUser.getUserId(), testAddress, 50000);
        int savedOrderId = orderRepository.save(order);
        assertTrue(savedOrderId>0);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("유저별 주문 내역 패이징 조회 및 총 갯수 테스트")
    void testFindAllByUserId() {
        orderRepository.save(new Order(testUser.getUserId(), testAddress, 10000));
        orderRepository.save(new Order(testUser.getUserId(), testAddress, 20000));
        orderRepository.save(new Order(testUser.getUserId(), testAddress, 30000));

        Page<Order> page = orderRepository.findAllByUserId(testUser.getUserId(), 1, 10);
        long totalCount = orderRepository.totalCountByUserId(testUser.getUserId());

        assertNotNull(page);
        assertEquals(3, totalCount);
        assertEquals(3, page.getContent().size());
        assertEquals(totalCount, page.getTotalCount());
    }
}
