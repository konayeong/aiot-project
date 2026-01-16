package com.nhnacademy.common.bounds;

public class RectangleBounds extends Bounds{
    private double minX;
    private double minY;
    private double width;
    private double height;

    public RectangleBounds(double minX, double minY, double width, double height) {
        if(width <=0 || height <= 0) {
            throw new IllegalArgumentException("너비와 높이는 0보다 커야 합니다.");
        }
    }

    @Override
    public double getMinX() {
        return minX;
    }

    @Override
    public double getMinY() {
        return minY;
    }

    @Override
    public double getMaxX() {
        return minX + width;
    }

    @Override
    public double getMaxY() {
        return minY + height;
    }
}
