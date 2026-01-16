package com.nhnacademy.common.balls;

import com.nhnacademy.common.Point;
import com.nhnacademy.interfaces.Paintable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball implements Paintable {
    private Point center;
    private final double radius;
    private Color color;

    public Ball(Point center, double radius) {
        if (center == null) {
            throw new IllegalArgumentException("Point는 null이 될 수 없습니다.");
        }

        if (radius <= 0) {
            throw new IllegalArgumentException("radius는 0보다 커야 합니다.");
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

    @Override
    public void paint(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(center.getX() - radius, center.getY() - radius,
                    radius * 2, radius * 2);
    }

}