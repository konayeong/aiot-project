package com.nhnacademy.common.boxs;

import com.nhnacademy.common.Point;
import com.nhnacademy.common.Vector2D;

public class MovableBox extends Box {

    private Vector2D velocity;
    
    public MovableBox(Point point, double width, double height, Vector2D velocity) {
        super(point, width, height);
        this.velocity = velocity;
    }

    public MovableBox(Point point, double width, double height) {
        this(point, width, height, new Vector2D(0,0 ));
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public void move(double deltaTime) {
        Point currentPosition = getPosition();
        Vector2D displacement = new Vector2D(velocity.getX() * deltaTime, velocity.getY() * deltaTime);
        Point newPosition = currentPosition.add(displacement);
        setPosition(newPosition);
    }
}
