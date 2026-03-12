package com.nhnacademy.shoppingmall.point.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class PointHistory {
    public enum PointType {
        REGISTER(1),
        LOGIN(2),
        ORDER_USE(3),
        ORDER_REWARD(4);

        private final int value;

        PointType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static PointType valueOf(int value) {
            for (PointType type : PointType.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown PointType value: " + value);
        }
    }

    @Setter
    private int pointHistoryId;
    private String userId;
    private Integer orderId;
    private PointType pointType;
    private int amount;
    private LocalDateTime createdAt;

    public PointHistory(String userId, Integer orderId, PointType pointType, int amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.pointType = pointType;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }
}
