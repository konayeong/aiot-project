package com.nhnacademy.common.boxs;

import com.nhnacademy.breakout.common.Box;
import com.nhnacademy.breakout.common.Vector2D;
import com.nhnacademy.breakout.interfaces.Movable;
import com.nhnacademy.common.Point;

import javafx.scene.paint.Color;

public class MovableBox extends Box implements Movable{

    private Vector2D velocity;
    
    public MovableBox(Point point, double width, double height, Vector2D velocity) {
        super(point, width, height);
        this.velocity = velocity;
    }

    public MovableBox(Point point, double width, double height, Color color) {
        super(point, width, height,color);
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    @Override
    public void move(double deltaTime) {
        Point pos = getPosition();
        Point next = pos.add(velocity.multiply(deltaTime));
        setPosition(next);
    }

}
