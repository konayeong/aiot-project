package com.nhnacademy.shoppingmall.cart.domain;

import com.nhnacademy.shoppingmall.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;
}
