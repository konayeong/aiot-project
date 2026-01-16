package com.nhnacademy.common;

import com.nhnacademy.common.interfaces.Movable;
import com.nhnacademy.common.interfaces.Paintable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ball implements Paintable, Movable{
    private Point cetner;
    private double radius;
    private Vector2D velocity;
    private Color color;
    private CollisionAction collisionAction;

    // TODO 생성자 추가

    @Override
    public void paint(GraphicsContext gc) {
        // TODO 원 그리기
    }

    @Override
    public void move(double deltaTime) { // TODO 위치 업데이트 ( 위치 += 속도 * 시간)

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
