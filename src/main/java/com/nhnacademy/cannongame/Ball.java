package com.nhnacademy.cannongame;

public class Ball {
    private final double x;
    private final double y;
    private final double radius;

    public Ball(double x, double y, double radius) {
        if(radius <= 0) {
            throw new IllegalArgumentException("반지름은 0보다 커야 합니다.");
        }
        
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }
}
