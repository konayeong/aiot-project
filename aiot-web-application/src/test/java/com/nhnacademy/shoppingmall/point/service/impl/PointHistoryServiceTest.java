package com.nhnacademy.shoppingmall.point.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.exception.PointHistoryDeleteException;
import com.nhnacademy.shoppingmall.point.exception.PointHistorySaveException;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PointHistoryServiceTest {
    PointHistoryRepository pointRepository = Mockito.mock(PointHistoryRepository.class);
    PointHistoryService pointService = new PointHistoryServiceImpl(pointRepository);

    @Test
    @DisplayName("포인트 내역 저장 Service 테스트")
    void testSavePointHistoryService() {
        PointHistory history = new PointHistory("test_user", null, PointHistory.PointType.LOGIN, 10000);

        when(pointRepository.save(any(PointHistory.class))).thenReturn(1);

        pointService.savePointHistory(history);

        verify(pointRepository, times(1)).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("포인트 내역 저장 Service 테스트 - 실패")
    void testSavePointHistoryService_Fail() {
        PointHistory history = new PointHistory("test_user", null, PointHistory.PointType.LOGIN, 1000);

        when(pointRepository.save(any(PointHistory.class))).thenReturn(0);

        PointHistorySaveException exception = assertThrows(PointHistorySaveException.class, () -> pointService.savePointHistory(history));
        assertEquals("Point history save failed for user: test_user", exception.getMessage());
    }

    @Test
    @DisplayName("포인트 내역 삭제")
    void testDeletePointHistory() {
        String userId = "test_user";
        when(pointRepository.deleteByUserId(userId)).thenReturn(5); // 5건 삭제되었다고 가정

        assertDoesNotThrow(() -> pointService.deletePointHistory(userId));

        verify(pointRepository, times(1)).deleteByUserId(userId);
    }

    @Test
    @DisplayName("포인트 내역 삭제 - 실패(잘못된 userId)")
    void testDeletePointHistory_Fail_InvalidUserId() {
        String emptyUserId = " ";

        assertThrows(UserNotFoundException.class, () -> pointService.deletePointHistory(emptyUserId));

        verify(pointRepository, never()).deleteByUserId(emptyUserId);
    }

    @Test
    @DisplayName("포인트 내역 삭제 - 실패(삭제된 행 0개)")
    void testDeletePointHistory_Fail_NoRowDeleted() {
        String userId = "test_user";
        when(pointRepository.deleteByUserId(userId)).thenReturn(0);

        PointHistoryDeleteException exception = assertThrows(PointHistoryDeleteException.class, () -> pointService.deletePointHistory(userId));
        assertEquals("Point history delete failed for user: test_user", exception.getMessage());
    }

    @Test
    @DisplayName("포인트 내역 페이징 조회")
    void testGetPointHistoryPage() {
        // given
        String userId = "test_user";
        int page = 1;
        int pageSize = 10;

        // 가짜 Page 객체 생성
        Page<PointHistory> mockPage = new Page<>(Collections.emptyList(), 0);
        when(pointRepository.findAllByUserId(userId, page, pageSize)).thenReturn(mockPage);

        // when
        Page<PointHistory> resultPage = pointService.getPointHistoryPage(userId, page, pageSize);

        // then
        assertNotNull(resultPage);
        assertEquals(mockPage, resultPage); // Repository가 반환한 객체를 Service가 그대로 잘 전달했는지 확인
        verify(pointRepository, times(1)).findAllByUserId(userId, page, pageSize);
    }
}
