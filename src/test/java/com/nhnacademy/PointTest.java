package com.nhnacademy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PointTest {
    private Point p1;
    private Point p2;

    @BeforeEach
    void setUp() {
        p1 = new Point(0, 0);
        p2 = new Point(3,4);
    }

    @Test
    @DisplayName("생성자 테스트")
    void testConstructor(){
        assertEquals(0, p1.getX());
        assertEquals(0, p1.getY());
    }

    @Test
    @DisplayName("거리 게산 테스트")
    void testDistanceTo() {
        double distance = p1.distanceTo(p2);
        assertEquals(5, distance);
    }

    @Test
    @DisplayName("null 입력 시 예외 발생 테스트")
    void testDistanceToNull() {
        assertThrows(IllegalArgumentException.class,
            () -> p1.distanceTo(null)
        );
    }
}
