package com.nhnacademy.breakout.common;

import com.nhnacademy.common.Point;
import com.nhnacademy.common.enums.CollisionAction;

import javafx.scene.paint.Color;

/**
 * 게임 내 정적인 충돌 오브젝트 (벽돌, 벽, 패들 등 기반)
 */
public class StaticObject extends Box {

    public StaticObject(Point point, double width, double height, Color color, CollisionAction action) {
        super(point, width, height, color, action);
    }

    public StaticObject(Point point, double width, double height, Color color) {
        super(point, width, height, color, CollisionAction.BOUNCE);
    }

    public StaticObject(Point point, double width, double height) {
        super(point, width, height, Color.GRAY, CollisionAction.BOUNCE);
    }

    @Override
    public void move(double deltaTime) {
    }

    @Override
    public void setVelocity(Vector2D velocity) {
        super.setVelocity(new Vector2D(0, 0));
    }
}

