package com.nhnacademy.shoppingmall.point.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class PointHistoryRepositoryTest {
    UserRepository userRepository = new UserRepositoryImpl();
    PointHistoryRepository pointRepository = new PointHistoryRepositoryImpl();

    User testUser;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();

        testUser = new User("nhnacademy-test-user","nhn아카데미","nhnacademy-test-password","19900505", User.Auth.ROLE_USER,100_0000, LocalDateTime.now(),null);
        userRepository.save(testUser);
    }
    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("포인트 내역 저장 테스트")
    void testSavePointHistory() {
        PointHistory history = new PointHistory(testUser.getUserId(), null, PointHistory.PointType.REGISTER, 1000000);

        int result = pointRepository.save(history);

        assertTrue(result > 0);
    }

    @Test
    @Order(2)
    @DisplayName("포인트 내역 페이징 및 최근 내역 내림차순 정렬")
    void testFindAllByUserId() throws InterruptedException {


        PointHistory history1 = new PointHistory(testUser.getUserId(), null, PointHistory.PointType.LOGIN, 1000);
        pointRepository.save(history1);

        Thread.sleep(1500);

        PointHistory history2 = new PointHistory(testUser.getUserId(), null, PointHistory.PointType.ORDER_REWARD, 2000);
        pointRepository.save(history2);

        Page<PointHistory> page = pointRepository.findAllByUserId(testUser.getUserId(), 1, 10);

        assertEquals(2, page.getContent().size());
        assertEquals(2000, page.getContent().get(0).getAmount());
        assertEquals(1000, page.getContent().get(1).getAmount());
    }

    @Test
    @Order(3)
    @DisplayName("특정 유저의 모든 포인트 내역 삭제 테스트")
    void testDeleteByUserId() {
        PointHistory history = new PointHistory(testUser.getUserId(), null, PointHistory.PointType.LOGIN, 1000);
        pointRepository.save(history);

        int deletedCount = pointRepository.deleteByUserId(testUser.getUserId());

        assertEquals(1, deletedCount);
        assertEquals(0, pointRepository.totalCountByUserId(testUser.getUserId()));
    }
}
