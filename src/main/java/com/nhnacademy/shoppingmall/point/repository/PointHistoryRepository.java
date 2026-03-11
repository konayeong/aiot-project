package com.nhnacademy.shoppingmall.point.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;

public interface PointHistoryRepository {
    int save(PointHistory pointHistory);
    int deleteByUserId(String userId);
    Page<PointHistory> findAllByUserId(String userId, int page, int pageSize);
    long totalCountByUserId(String userId);
}
