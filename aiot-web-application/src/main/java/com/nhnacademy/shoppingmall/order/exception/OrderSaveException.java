package com.nhnacademy.shoppingmall.order.exception;

public class OrderSaveException extends RuntimeException {
    public OrderSaveException(String userId) {
        super(String.format("Order save failed for user: %s", userId));
    }
}
