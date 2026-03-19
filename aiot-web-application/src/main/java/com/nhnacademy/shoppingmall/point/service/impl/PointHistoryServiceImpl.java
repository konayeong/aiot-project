package com.nhnacademy.shoppingmall.point.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.exception.PointHistoryDeleteException;
import com.nhnacademy.shoppingmall.point.exception.PointHistorySaveException;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;

public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;

    public PointHistoryServiceImpl(PointHistoryRepository pointHistoryRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Override
    public void savePointHistory(PointHistory pointHistory) {
        int result = pointHistoryRepository.save(pointHistory);
        if(result < 1) {
            throw new PointHistorySaveException(pointHistory.getUserId());
        }
    }

    @Override
    public void deletePointHistory(String userId) {
        if(userId == null || userId.isBlank()) {
            throw new UserNotFoundException(userId);
        }

        int result = pointHistoryRepository.deleteByUserId(userId);
        if(result < 1) {
            throw new PointHistoryDeleteException(userId);
        }
    }

    @Override
    public Page<PointHistory> getPointHistoryPage(String userId, int page, int pageSize) {
        return pointHistoryRepository.findAllByUserId(userId, page, pageSize);
    }
}
