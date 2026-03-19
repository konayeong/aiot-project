package com.nhnacademy.common;

public abstract class Vector {
    public abstract double get(int index);
    public abstract void set(int index, double value);
    public abstract Vector createNew();

    public abstract int dimension(); // n차원 지원

    public double magnitude() {
        double sum = 0.0;
        for (int i = 0; i < dimension(); i++) {
            sum += Math.pow(get(i), 2);
        }
        return Math.sqrt(sum);
    }

    public void normalize() {
        double mag = magnitude();
        if (mag == 0) return;

        for (int i = 0; i < dimension(); i++) {
            set(i, get(i) / mag);
        }
    }


    public double dot(Vector other) {
        if (dimension() != other.dimension()) {
            throw new IllegalArgumentException("Vector dimension mismatch");
        }
        double sum = 0.0;
        for (int i = 0; i < dimension(); i++) {
            sum += get(i) * other.get(i);
        }
        return sum;
    }
}
