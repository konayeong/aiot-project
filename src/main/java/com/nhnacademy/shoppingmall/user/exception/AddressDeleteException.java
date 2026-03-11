package com.nhnacademy.shoppingmall.user.exception;

public class AddressDeleteException extends RuntimeException {
    public AddressDeleteException(int addressId) {
        super(String.format("Address delete failed for addressId: %d", addressId));
    }
}
