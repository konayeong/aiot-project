package com.nhnacademy.common;

import com.nhnacademy.common.interfaces.Collidable;
import com.nhnacademy.common.interfaces.Paintable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Box implements Paintable, Collidable{
    private Point position;
    private double width;
    private double height;
    private Color color;
    private CollisionAction collisionAction;

    @Override
    public void paint(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(position.getX(), position.getY(), width, height);
    }

    @Override
    public void handleCollision(Collidable other) {
        this.collisionAction = other.getCollisionAction();
    }

    @Override
    public CollisionAction getCollisionAction() {
        return collisionAction;
    }

}
