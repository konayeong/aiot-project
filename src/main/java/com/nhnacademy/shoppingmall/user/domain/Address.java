package com.nhnacademy.shoppingmall.user.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Address {
    @Setter
    private int addressId;
    private String userId;
    private String address;

    public Address(String userId, String address) {
        this.userId = userId;
        this.address = address;
    }
}
