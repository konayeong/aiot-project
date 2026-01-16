package com.nhnacademy.world;

import java.util.ArrayList;
import java.util.List;

import com.nhnacademy.common.Ball;
import com.nhnacademy.common.PaintableBall;
import com.nhnacademy.common.Point;

import javafx.scene.canvas.GraphicsContext;

public class World {
    private final double width;
    private final double height;
    List<Ball> balls = new ArrayList<>();

    public World(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void add(Ball ball) {
        if (ball == null) {
            throw new IllegalArgumentException("Ball cannot be null");
        }

        if (isInBounds(ball) == false) {
            throw new IllegalArgumentException("Ball is out of world bounds");
        }

        balls.add(ball);
    }

    public void remove(Ball ball) {
        balls.remove(ball);
    }

    public List<Ball> getBalls() {
        return new ArrayList<>(balls);
    }

    public int getBallCount() {
        return balls.size();
    }

    public void clear() {
        balls.clear();
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, width, height);

        for (Ball ball : balls) {
            if (ball instanceof PaintableBall paintableBall) {
                paintableBall.draw(gc);
            }
        }
    }

    protected boolean isInBounds(Ball ball) {
        Point center = ball.getCenter();
        double radius = ball.getRadius();
        return center.getX() - radius >= 0 &&
                center.getX() + radius <= width &&
                center.getY() - radius >= 0 &&
                center.getY() + radius <= height;
    }
}