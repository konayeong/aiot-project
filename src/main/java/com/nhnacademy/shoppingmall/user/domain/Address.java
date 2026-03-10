package com.nhnacademy.shoppingmall.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Address {
    private int addressId;
    private String userId;
    private String address;

    public Address(String userId, String address) {
        this.userId = userId;
        this.address = address;
    }
}
