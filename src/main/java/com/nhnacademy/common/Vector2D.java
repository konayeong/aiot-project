package com.nhnacademy.common;

public class Vector2D {
    private final double x;
    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this(0, 0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(getX() + other.getX(), getY() + other.getY());
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(getX() - other.getX(), getY() - other.getY());
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(getX() * scalar, getY() * scalar);
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
    }

    public double angle() {
        return Math.toDegrees(Math.atan2(getY(), getX()));
    }

    public Vector2D normalize() {
        double value = magnitude();
        return new Vector2D(getX() / value, getY() / value);
    }

    public double dot(Vector2D other) {
        return getX() * other.getX() + getY() * other.getY();
    }
}