package com.nhnacademy.cannongame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BallTest {
    private Ball ball;

    @BeforeEach
    public void setUp() {
        ball = new Ball(100, 100, 10);
    }

    @Test
    @DisplayName("생성자 테스트")
    void testConstructor() {
        assertEquals(100, ball.getX());
        assertEquals(100, ball.getY());
        assertEquals(10, ball.getRadius());
    }

    @Test
    @DisplayName("반지름 검증 테스트")
    void testInvalidRadius() {
        assertThrows(IllegalArgumentException.class, () -> new Ball(0, 0, -5));
    }
}
