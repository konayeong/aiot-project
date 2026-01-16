package com.nhnacademy.common;

public class Ball {
    private Point center;
    private final double radius;

    public Ball(Point center, double radius) {
        if (center == null) {
            throw new IllegalArgumentException("Center point cannot be null");
        }

        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        this.center = center;
        this.radius = radius;
    }

    public Ball(double x, double y, double radius) {
        this(new Point(x, y), radius);
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public void moveTo(Point newCenter) {
        center = newCenter;
    }

    public boolean contains(Point point) {
        return center.distanceTo(point) <= radius;
    }

    public boolean contains(double x, double y) {
        return contains(new Point(x, y));
    }
}