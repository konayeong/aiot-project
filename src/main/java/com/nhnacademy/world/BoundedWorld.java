package com.nhnacademy.world;

import com.nhnacademy.common.Vector2D;
import com.nhnacademy.common.balls.Ball;
import com.nhnacademy.common.balls.MovableBall;

public class BoundedWorld extends MovableWorld{
    
    public BoundedWorld(int width, int height) {
        super(width, height);
    }

    @Override
    public void update() {
        for(Ball ball : getBalls()) {
            if(ball instanceof MovableBall movableBall) {
                movableBall.move();

                if (!isInBounds(movableBall)) {
                    double minX = movableBall.getCenter().getX() - movableBall.getRadius();
                    double maxX = movableBall.getCenter().getX() + movableBall.getRadius();
                    double minY = movableBall.getCenter().getY() - movableBall.getRadius();
                    double maxY = movableBall.getCenter().getY() + movableBall.getRadius();

                    if (minX < 0 || maxX > getWidth()) {
                        Vector2D velocity = movableBall.getVelocity();
                        movableBall.setVelocity(new Vector2D(-velocity.getX(), velocity.getY()));
                    }

                    if (minY < 0 || maxY > getHeight()) {
                        Vector2D velocity = movableBall.getVelocity();
                        movableBall.setVelocity(new Vector2D(velocity.getX(), -velocity.getY()));
                    }
                }
            }
        }
    }
}
