package com.nhnacademy.common.bounds;

import com.nhnacademy.common.Point;

public abstract class Bounds {
    public abstract double getMinX();
    public abstract double getMinY();
    public abstract double getMaxX();
    public abstract double getMaxY();

    public double getWidth() {
        return getMaxX() - getMinX();
    }

    public double getHeight() {
        return getMaxY() - getMinY();
    }

    public double getCenterX() {
        return getMinX() + (getWidth() / 2.0);
    }

    public double getCenterY() {
        return getMinY() + (getHeight() / 2.0);
    }

    public boolean contains(Point point) {
        double pointX = point.getX();
        double pointY = point.getY();

        return pointX >= getMinX() && pointX <= getMaxX()
                && pointY >= getMinY() && pointY <= getMaxY();
    }

    public boolean contains(Bounds other) {
        return other.getMinX() >= getMinX() &&
                other.getMaxX() <= getMaxX() &&
                other.getMinY() >= getMinY() &&
                other.getMaxY() <= getMaxY();
    }

    public boolean intersects(Bounds other) {
        return !(other.getMaxX() < getMinX() ||
                other.getMinX() > getMaxX() ||
                other.getMaxY() < getMinY() ||
                other.getMinY() > getMaxY());
    }
}
