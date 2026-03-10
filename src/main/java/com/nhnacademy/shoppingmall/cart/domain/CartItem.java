package com.nhnacademy.shoppingmall.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItem {
    private int productId;
    private int quantity;
}
