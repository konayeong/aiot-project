package com.nhnacademy.common.bounds;

public class RectangleBounds extends Bounds {
    private final double minX;
    private final double minY;
    private final double width;
    private final double height;

    public RectangleBounds(double minX, double minY, double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("너비와 높이는 0보다 커야 합니다.");
        }

        this.minX = minX;
        this.minY = minY;
        this.width = width;
        this.height = height;
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

    /**
     * Rect-Rect 충돌 검사 (AABB 방식)
     */
    public boolean intersects(Bounds other) {
        return !(getMaxX() < other.getMinX() ||
                 getMinX() > other.getMaxX() ||
                 getMaxY() < other.getMinY() ||
                 getMinY() > other.getMaxY());
    }
}
