package com.nhnacademy.breakout.common;

import com.nhnacademy.breakout.interfaces.Boundable;
import com.nhnacademy.breakout.interfaces.Collidable;
import com.nhnacademy.breakout.interfaces.Movable;
import com.nhnacademy.breakout.interfaces.Paintable;
import com.nhnacademy.common.Point;
import com.nhnacademy.common.bounds.Bounds;
import com.nhnacademy.common.bounds.CircleBounds;
import com.nhnacademy.common.enums.CollisionAction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball implements Paintable, Movable, Collidable {
    private Point center;
    private final double radius;
    private Vector2D velocity;
    private Color color;
    private CollisionAction collisionAction;
    private boolean destroyed = false;
    
    public Ball(Point center, double radius, Color color) {
        if (center == null) throw new IllegalArgumentException("center는 null일 수 없습니다.");
        if (radius <= 0) throw new IllegalArgumentException("radius는 0보다 커야 합니다.");
        if (color == null) throw new IllegalArgumentException("color는 null일 수 없습니다.");

        this.center = center;
        this.radius = radius;
        this.velocity = Vector2D.zero();
        this.color = color;
        this.collisionAction = CollisionAction.BOUNCE;
    }

    public Ball(Point center, double radius) {
        this(center, radius, Color.BLUE);
    }

    public Ball(double x, double y, double radius, Color color) {
        this(new Point(x, y), radius, color);
    }

    public Ball(double x, double y, double radius) {
        this(new Point(x, y), radius);
    }


    // Paintable
    @Override
    public void paint(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(
                center.getX() - radius,
                center.getY() - radius,
                radius * 2,
                radius * 2
        );
    }

    // Movable
    @Override
    public void move(double dt) {
        center = new Point(
                center.getX() + velocity.getX() * dt,
                center.getY() + velocity.getY() * dt
        );
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    // Collidable + Boundable
    @Override
    public Bounds getBounds() {
        return new CircleBounds(center.getX(), center.getY(), radius);
    }

    @Override
    public boolean isColliding(Boundable other) {
        return this.getBounds().intersects(other.getBounds());
    }

    public boolean collidesWith(Collidable other) {
        return this.getBounds().intersects(other.getBounds());
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
    public void handleCollision(Collidable other) {
        switch (collisionAction) {
            case BOUNCE -> bounce(other);
            case DESTROY -> destroyed = true;
            case STOP -> velocity = Vector2D.zero();
            case PASS -> {} // 아무것도 안함
            case CUSTOM -> {
                // 외부에서 처리
            }
        }
    }

    private void bounce(Collidable boundary) {
        Bounds b = boundary.getBounds();
        Bounds me = this.getBounds();

        boolean hitLeft   = me.getMaxX() >= b.getMinX() && me.getMinX() < b.getMinX();
        boolean hitRight  = me.getMinX() <= b.getMaxX() && me.getMaxX() > b.getMaxX();
        boolean hitTop    = me.getMaxY() >= b.getMinY() && me.getMinY() < b.getMinY();
        boolean hitBottom = me.getMinY() <= b.getMaxY() && me.getMaxY() > b.getMaxY();

        if (hitLeft || hitRight) {
            velocity = new Vector2D(-velocity.getX(), velocity.getY());
        }
        if (hitTop || hitBottom) {
            velocity = new Vector2D(velocity.getX(), -velocity.getY());
        }
    }
    public void moveTo(Point p) {
        this.center = p;
    }

    public boolean contains(Point p) {
        return center.distanceTo(p) <= radius;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Point getCenter() { return center; }
    public double getRadius() { return radius; }
    public double getX() { return center.getX(); }
    public double getY() { return center.getY(); }
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
}
