package com.nhnacademy.common;

import com.nhnacademy.common.interfaces.Movable;

public class MovableBox extends Box implements Movable{
    private Vector2D velocity;

    @Override
    public void move(double deltaTime) {
        
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
    
}
