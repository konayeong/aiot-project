package com.nhnacademy.breakout.common;

import com.nhnacademy.common.Vector;

public class Vector2D extends Vector{
    private double x;
    private double y;

    public Vector2D() {
        this(0, 0);
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
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

    public double angle() {
        return Math.toDegrees(Math.atan2(getY(), getX()));
    }

    public Vector2D rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double x = this.getX() * cos - this.getY() * sin;
        double y = this.getX() * sin + this.getY() * cos;

        return new Vector2D(x, y);
    }

    public double cross(Vector2D other) {
        return x * other.getY() - y * other.getX();
    }

    public static Vector2D fromPolar(double magnitude, double angle) {
        double x = magnitude * Math.cos(angle);
        double y = magnitude * Math.sin(angle);
        return new Vector2D(x, y);
    }

    public static Vector2D zero(){ return new Vector2D(); }

    public static Vector2D unitX() { return new Vector2D(1,0); }

    public static Vector2D unitY(){ return new Vector2D(0,1); }
    
    @Override
    public double get(int index) {
        return switch (index) {
            case 0 -> x;
            case 1 -> y;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public void set(int index, double value) {
        switch (index) {
            case 0 -> x = value;
            case 1 -> y = value;
            default -> throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Vector createNew() {
        return new Vector2D();
    }

    @Override
    public int dimension() {
        return 2;
    }
}