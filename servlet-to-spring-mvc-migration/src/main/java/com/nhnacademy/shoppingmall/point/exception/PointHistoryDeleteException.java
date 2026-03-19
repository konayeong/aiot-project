package com.nhnacademy.shoppingmall.point.exception;

public class PointHistoryDeleteException extends RuntimeException {
    public PointHistoryDeleteException(String userId) {
        super(String.format("Point history delete failed for user: %s", userId));
    }
}
