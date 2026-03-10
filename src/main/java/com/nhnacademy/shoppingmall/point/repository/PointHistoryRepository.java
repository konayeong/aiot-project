package com.nhnacademy.shoppingmall.point.repository;

import com.nhnacademy.shoppingmall.point.domain.PointHistory;

public interface PointHistoryRepository {
    int save(PointHistory pointHistory);
}
