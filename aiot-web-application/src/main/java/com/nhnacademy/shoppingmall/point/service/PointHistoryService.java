package com.nhnacademy.shoppingmall.point.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;

public interface PointHistoryService {
    void savePointHistory(PointHistory pointHistory);
    void deletePointHistory(String userId);
    Page<PointHistory> getPointHistoryPage(String userId, int page, int pageSize);
}
