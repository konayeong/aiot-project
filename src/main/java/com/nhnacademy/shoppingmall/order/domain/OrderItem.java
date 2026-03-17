package com.nhnacademy.shoppingmall.order.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int productId;
    private String productName;
    private int price;
    private int quantity;
    private String image;

    // 생성
    public OrderItem(String productName, int price, int quantity, String image) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }
}
