package com.nhnacademy.common;

public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Point other) {
        double x1 = other.x - this.x;
        double y1 = other.y - this.y;
        return Math.sqrt((x1 * x1) + (y1 * y1));
    }

    public Point add(Vector2D vector) {
        return new Point(getX() + vector.getX(), getY() + vector.getY());
    }

    public Point subtract(Vector2D vector) {
        return new Point(getX() - vector.getX(), getY() - vector.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point other = (Point) obj;
        return Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0;
    }
}