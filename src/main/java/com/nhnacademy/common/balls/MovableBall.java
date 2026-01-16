package com.nhnacademy.common.balls;

import com.nhnacademy.common.Point;
import com.nhnacademy.common.Vector2D;

import javafx.scene.paint.Color;

public class MovableBall extends PaintableBall {
    public static Color DEFAULT_COLOR = Color.BLUE;
    private Vector2D velocity;

    public MovableBall(Point center, double radius, Color color, Vector2D velocity) {
        super(center, radius, color);

        if (velocity == null) {
            throw new IllegalArgumentException();
        }

        this.velocity = velocity;

    }

    public MovableBall(Point center, double radius) {
        this(center, radius, MovableBall.DEFAULT_COLOR);
    }

    public MovableBall(Point center, double radius, Color color) {
        this(center, radius, color, new Vector2D(0, 0));
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D newVelocity) {
        velocity = newVelocity;
    }

    public void move() {
        moveTo(getCenter().add(getVelocity()));
    }

    public void move(double deltaTime) {
        double frames = deltaTime / (1000 / 60);

        moveTo(getCenter().add(getVelocity().multiply(frames)));
    }
}