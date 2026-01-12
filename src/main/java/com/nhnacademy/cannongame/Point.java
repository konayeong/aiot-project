package com.nhnacademy.cannongame;

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
        if (other == null) {
            throw new IllegalArgumentException("포인트가 null일 수 없음");
        }

        double subX = other.getX() - x;
        double subY = other.getY() - y;

        return Math.sqrt(subX * subX + subY * subY);
        // return Math.sqrt(Math.pow(subX, 2) + Math.pow(subY, 2));
    }
}
