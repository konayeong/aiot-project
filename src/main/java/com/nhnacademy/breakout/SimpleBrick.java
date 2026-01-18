package com.nhnacademy.breakout;

import com.nhnacademy.breakout.common.StaticObject;
import com.nhnacademy.breakout.interfaces.Breakable;
import com.nhnacademy.common.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 한 번에 깨지는 기본 벽돌
 */
public class SimpleBrick extends StaticObject implements Breakable {
    private boolean destroyed = false;
    private int points;

    public SimpleBrick(double x, double y, double width, double height, Color color, int points) {
        super(new Point(x,y), width, height, color);
        this.points = points;
    }

    @Override
    public void hit(int damage) {
        destroyed = true;
    }

    @Override
    public boolean isBroken() {
        return destroyed;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void paint(GraphicsContext gc) {
        if (!destroyed) {
            // 3D 효과: 그림자
            gc.setFill(getColor().darker());
            gc.fillRect(getX() + 2, getY() + 2, getWidth(), getHeight());

            // 본체
            gc.setFill(getColor());
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
