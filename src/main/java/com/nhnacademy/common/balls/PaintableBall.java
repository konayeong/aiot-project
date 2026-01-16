package com.nhnacademy.common.balls;

import com.nhnacademy.common.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PaintableBall extends Ball {
    public static final Color DEFAULT_COLOR = Color.RED;
    private Color color;

    public PaintableBall(Point center, double radius, Color color) {
        super(center, radius);
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
        this.color = color;
    }

    public PaintableBall(double x, double y, double radius, Color color) {
        this(new Point(x, y), radius, color);
    }

    public PaintableBall(Point center, double radius) {
        this(center, radius, DEFAULT_COLOR);
    }

    public PaintableBall(double x, double y, double radius) {
        this(x, y, radius, DEFAULT_COLOR);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
        this.color = color;
    }

    public void draw(GraphicsContext gc) {
        double leftX = getCenter().getX() - getRadius();
        double topY = getCenter().getY() - getRadius();
        double diameter = getRadius() * 2;

        Paint originalPaint = gc.getFill();
        gc.setFill(color);
        gc.fillOval(leftX, topY, diameter, diameter);
        gc.setFill(originalPaint);
    }
}