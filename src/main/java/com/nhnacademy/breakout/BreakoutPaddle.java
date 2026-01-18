package com.nhnacademy.breakout;

import com.nhnacademy.breakout.common.Ball;
import com.nhnacademy.breakout.common.Box;
import com.nhnacademy.common.Point;

import javafx.scene.paint.Color;

public class BreakoutPaddle extends Box {
    private double speed = 600; // px/s
    private double targetX;

    // PowerUp 관련 필드
    private double originalWidth;
    private double remainingExpandDuration;

    public BreakoutPaddle(double x, double y, double width, double height, Color color, double speed) {
        super(new Point(x, y), width, height, color);
        this.speed = speed;
        this.targetX = x + width / 2.0;
        this.originalWidth = width;
        this.remainingExpandDuration = 0;
    }

    public void setTargetX(double mouseX) {
        this.targetX = mouseX;
    }

    /**
     * EX: scale = 1.5 → 50% 증가
     */
    public void expand(double scale, double duration) {
        double center = getX() + getWidth() / 2.0;

        double newWidth = originalWidth * scale;
        setWidth(newWidth);
        setX(center - newWidth / 2.0);

        this.remainingExpandDuration = duration;
    }

    private void restore() {
        double center = getX() + getWidth() / 2.0;
        setWidth(originalWidth);
        setX(center - originalWidth / 2.0);
    }

    public void update(double dt) {
        // 효과 지속 시간 처리
        if (remainingExpandDuration > 0) {
            remainingExpandDuration -= dt;
            if (remainingExpandDuration <= 0) {
                restore();
            }
        }

        // Paddle movement
        move(dt);
    }

    private void setX(double x) {
        setPosition(new Point(x, getY()));
    }

    @Override
    public void move(double dt) {
        double centerX = getX() + getWidth() / 2.0;
        double diff = targetX - centerX;
        double moveAmount = speed * dt;

        if (Math.abs(diff) <= moveAmount) {
            setX(targetX - getWidth() / 2.0);
        } else {
            setX(getX() + Math.signum(diff) * moveAmount);
        }
    }

    public double calculateReflectionAngle(Ball ball) {
        double hitPosition = (ball.getCenter().getX() - getX()) / getWidth();
        return (hitPosition - 0.5) * (Math.PI / 3);
    }
}
