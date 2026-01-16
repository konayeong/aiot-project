package com.nhnacademy.common.balls;

import com.nhnacademy.common.Point;
import com.nhnacademy.common.Vector2D;

import javafx.scene.canvas.GraphicsContext;

public class SimpleMovableBall extends AbstractBall {

    private Vector2D velocity;

    public SimpleMovableBall(double x, double y, double radius) {
        super(x, y, radius);
        this.velocity = new Vector2D(0, 0); // 초기 속도 0
    }

    public SimpleMovableBall(Point center, double radius, Vector2D velocity) {
        super(center, radius);
        this.velocity = velocity;
    }

    @Override
    protected void performUpdate(double deltaTime) {
        double newX = getCenter().getX() + velocity.getX() * deltaTime;
        double newY = getCenter().getY() + velocity.getY() * deltaTime;

        moveTo(new Point(newX, newY));
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void paint(GraphicsContext gc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'paint'");
    }
}
