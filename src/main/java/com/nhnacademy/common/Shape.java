package com.nhnacademy.common;

public abstract class Shape {
    protected Point position;
    
    public abstract double getArea();

    public abstract double getPerimeter();

    public abstract String getShapeType();

    public Point getPosition() {
        return position;
    }

    public void moveTo(Point newPosition) {
        position = newPosition;
    }

    // CHECKLIST 이게 맞나?
    public void moveBy(Vector2D delta) {
        position.add(delta);
    }
}
