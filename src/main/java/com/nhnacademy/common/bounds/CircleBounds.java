package com.nhnacademy.common.bounds;

public class CircleBounds extends Bounds{
    private double centerX;
    private double centerY;
    private double radius;

    public CircleBounds(double centerX, double centerY, double radius) {
        if(radius <= 0) {
            throw new IllegalArgumentException("반지름은 0보다 커야 합니다.");
        }
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public double getMinX() {
        return centerX - radius;
    }
    @Override
    public double getMinY() {
        return centerY - radius;
    }
    @Override
    public double getMaxX() {
        return centerX + radius;
    }
    @Override
    public double getMaxY() {
        return centerY + radius;
    }
}
