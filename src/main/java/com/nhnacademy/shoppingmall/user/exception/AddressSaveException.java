package com.nhnacademy.shoppingmall.user.exception;

public class AddressSaveException extends RuntimeException {
    public AddressSaveException(int addressId) {
        super(String.format("Address save failed for addressId: %d", addressId));
    }
}
