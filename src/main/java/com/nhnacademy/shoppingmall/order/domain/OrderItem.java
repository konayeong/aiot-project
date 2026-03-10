package com.nhnacademy.shoppingmall.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int productId;
    private int price;
    private int quantity;

    // 생성
    public OrderItem(int productId, int price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
