package com.nhnacademy.breakout;

import com.nhnacademy.breakout.common.StaticObject;
import com.nhnacademy.common.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 깨지지 않는 벽 또는 외곽 경계를 위한 벽돌
 */
public class UnbreakableBrick extends StaticObject {

    public UnbreakableBrick(Point point, double width, double height) {
        super(point, width, height, Color.DARKGRAY);
    }

    public UnbreakableBrick(double x, double y, double width, double height) {
        super(new Point(x, y), width, height, Color.DARKGRAY);
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
        // 테두리
        gc.setStroke(Color.BLACK);
        gc.strokeRect(getX(), getY(), getWidth(), getHeight());
    }
 }
