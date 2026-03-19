package com.nhnacademy.shoppingmall.point.exception;

public class PointHistorySaveException extends RuntimeException {
    public PointHistorySaveException(String userId) {
        super(String.format("Point history save failed for user: %s", userId));
    }
}
