package com.nhnacademy.common;

public abstract class Bounds {
    // 추상 메서드
    public abstract double getMinX();
    public abstract double getMinY();
    public abstract double getMaxX();
    public abstract double getMaxY();

    // 구체 메서드
    public double getWidth() {
        return getMaxX() - getMinX();
    }

    public double getHeight() {
        return getMaxY() - getMinY();
    }

    public double getCenterX() {
        return getMinX() + (getMaxX() - getMinX()) / 2.0;
    }

    public double getCenterY() {
        return getMinY() + (getMaxY() - getMinY()) / 2.0;
    }
    
    public boolean contains(Point point) {
        double x = point.getX();
        double y = point.getY();

        return x >= getMinX() && x <= getMaxX()
            && y >= getMinY() && y <= getMaxY();
    }

    public boolean contains(Bounds other) {
        return this.getMinX() <= other.getMinX() &&
               this.getMaxX() >= other.getMaxX() &&
               this.getMinY() <= other.getMinY() &&
               this.getMaxY() >= other.getMaxY();
    }

    
    public boolean intersects(Bounds other) { // 교차 여부
        return !(other.getMaxX() < this.getMinX() ||
                 other.getMinX() > this.getMaxX() ||
                 other.getMaxY() < this.getMinY() ||
                 other.getMinY() > this.getMaxY());
    }
}
