package com.nhnacademy.common.balls;

import com.nhnacademy.common.Point;
import com.nhnacademy.common.bounds.Bounds;
import com.nhnacademy.interfaces.Paintable;

public abstract class AbstractBall implements Paintable{
    private Point center;
    private final double radius;
    private Bounds bounds;

    public AbstractBall(Point center, double radius) {
        if (center == null) {
            throw new IllegalArgumentException("center가 null일 수 없습니다.");
        }

        if (radius <= 0) {
            throw new IllegalArgumentException("radius는 0보다 커야 합니다.");
        }

        this.center = center;
        this.radius = radius;
        updateBounds();
    }

    public AbstractBall(double x, double y, double radius) {
        this(new Point(x, y), radius);
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void moveTo(Point newCenter) {
        center = newCenter;
        updateBounds();
    }

    public boolean contains(Point point) {
        return center.distanceTo(point) <= radius;
    }

    public boolean contains(double x, double y) {
        return contains(new Point(x, y));
    }

    public final void update(double deltaTime) {
        beforeUpdate(deltaTime);
        performUpdate(deltaTime);
        afterUpdate(deltaTime);
        updateBounds();
    }

    protected void beforeUpdate(double deltaTime) {}
    protected void afterUpdate(double deltaTime) {}

    protected abstract void performUpdate(double deltaTime);

    /** 경계 업데이트 */
    protected void updateBounds() {
        bounds = new Bounds() {
            @Override public double getMinX() { return center.getX() - radius; }
            @Override public double getMinY() { return center.getY() - radius; }
            @Override public double getMaxX() { return center.getX() + radius; }
            @Override public double getMaxY() { return center.getY() + radius; }
        };
    }
}
