package com.nhnacademy.world;

import java.util.ArrayList;
import java.util.List;

import com.nhnacademy.breakout.common.Ball;
import com.nhnacademy.common.Point;
import com.nhnacademy.common.balls.PaintableBall;

import javafx.scene.canvas.GraphicsContext;

public class World {
    private final double width;
    private final double height;
    List<Ball> balls = new ArrayList<>();

    public World(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("너비와 높이는 0보다 커야 합니다.");
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
            throw new IllegalArgumentException("Ball은 null일 수 없습니다.");
        }

        if (isInBounds(ball) == false) {
            throw new IllegalArgumentException("Ball이 영역 밖에 존재합니다.");
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