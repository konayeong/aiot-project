package com.nhnacademy.shoppingmall.order.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
// 주문은 수정이 없다고 생각해서 @Setter 안 적음
@EqualsAndHashCode
@AllArgsConstructor
public class Order {
    @Setter
    private int orderId;
    private String userId;
    private int addressId;
    private int usedPoint;
    private LocalDateTime createdAt;

    public Order(String userId, int addressId, int usedPoint) {
        this.userId = userId;
        this.addressId = addressId;
        this.usedPoint = usedPoint;
        this.createdAt = LocalDateTime.now();
    }
}
