package com.nhnacademy.shoppingmall.cart.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CartItem {
    private int productId;  // Product product로 변경 예정
    private int quantity;
}
