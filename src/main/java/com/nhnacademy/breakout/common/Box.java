package com.nhnacademy.breakout.common;

import com.nhnacademy.breakout.interfaces.Collidable;
import com.nhnacademy.breakout.interfaces.Movable;
import com.nhnacademy.breakout.interfaces.Paintable;
import com.nhnacademy.common.Point;
import com.nhnacademy.common.bounds.RectangleBounds;
import com.nhnacademy.common.enums.CollisionAction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Box implements Paintable, Collidable, Movable {
    private Point position;
    private double width;
    private double height;
    private Color color;
    private CollisionAction collisionAction;
    private Vector2D velocity = new Vector2D();

    public Box(Point point, double width, double height, Color color, CollisionAction collisionAction) {
        if (point == null) {
            throw new IllegalArgumentException("Point는 null일 수 없습니다.");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("너비, 높이는 0보다 커야 합니다.");
        }
        if (color == null) {
            throw new IllegalArgumentException("Color는 null일 수 없습니다.");
        }

        this.position = point;
        this.width = width;
        this.height = height;
        this.color = color;
        this.collisionAction = collisionAction;
    }

    public Box(Point point, double width, double height, Color color) {
        this(point, width, height, color, CollisionAction.STOP);
    }

    public Box(Point point, double width, double height) {
        this(point, width, height, Color.GRAY, CollisionAction.STOP);
    }

    public double getX() {
         return position.getX(); 
    }
    public double getY() { 
        return position.getY(); 
    }
    public double getWidth() { 
        return width; 
    }
    public double getHeight() { 
        return height; 
    }
    public Color getColor() { 
        return color; 
    }
    public Point getPosition() { 
        return position; 
    }

    public void setPosition(Point position) { this.position = position; }
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }
    public void setColor(Color color) { this.color = color; }

    @Override
    public RectangleBounds getBounds() {
        return new RectangleBounds(position.getX(), position.getY(), width, height);
    }

    @Override
    public void handleCollision(Collidable other) {
        // CHECKLIST (Ball이나 Paddle에서 처리)
    }

    @Override
    public CollisionAction getCollisionAction() {
        return collisionAction;
    }

    @Override
    public void setCollisionAction(CollisionAction action) {
        this.collisionAction = action;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(position.getX(), position.getY(), width, height);
    }

    @Override
    public void move(double deltaTime) {
        position = new Point(
            position.getX() + velocity.getX() * deltaTime,
            position.getY() + velocity.getY() * deltaTime
        );
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector2D velocity) {
        if (velocity == null) velocity = new Vector2D(0, 0);
        this.velocity = velocity;
    }
}
