package com.nhnacademy.common.balls;

import com.nhnacademy.common.Point;
import com.nhnacademy.common.Vector2D;

import javafx.scene.paint.Color;

public class BoundedBall extends MovableBall {
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public BoundedBall(Point position, double radius, Color color, Vector2D velocity) {
        super(position, radius, color, velocity);
    }

    public void setBounds(double minX, double minY, double maxX, double maxY) {
        this.minX = minX + getRadius();
        this.maxX = maxX - getRadius();
        this.minY = minY + getRadius();
        this.maxY = maxY - getRadius();
    }

    @Override
    public void move() {
        super.move();

        // 경계 검사 및 반사 처리
        if (getCenter().getX() < minX) {
            moveTo(new Point(minX, getCenter().getY()));
            setVelocity(new Vector2D(-getVelocity().getX(), getVelocity().getY()));
        } else if (getCenter().getX() > maxX) {
            moveTo(new Point(maxX, getCenter().getY()));
            setVelocity(new Vector2D(-getVelocity().getX(), getVelocity().getY()));
        }

        if (getCenter().getY() < minY) {
            moveTo(new Point(getCenter().getX(), minY));
            setVelocity(new Vector2D(getVelocity().getX(), -getVelocity().getY()));
        } else if (getCenter().getY() > maxY) {
            moveTo(new Point(getCenter().getX(), maxY));
            setVelocity(new Vector2D(getVelocity().getX(), -getVelocity().getY()));
        }
    }
}