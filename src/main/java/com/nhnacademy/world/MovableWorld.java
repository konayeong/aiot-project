package com.nhnacademy.world;

import com.nhnacademy.common.balls.Ball;
import com.nhnacademy.common.balls.MovableBall;

import lombok.Getter;

@Getter
public class MovableWorld extends World{

    public MovableWorld(int width, int height) {
        super(width, height);
    }

    public void update() {
        for(Ball ball : getBalls()) {
            if(ball instanceof MovableBall movableBall) {
                movableBall.move();
            }
        }
    }
}
