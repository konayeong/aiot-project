package com.nhnacademy.world;

import java.util.List;

import com.nhnacademy.breakout.common.Ball;
import com.nhnacademy.breakout.common.Box;
import com.nhnacademy.common.balls.MovableBall;
import com.nhnacademy.common.balls.PaintableBall;
import com.nhnacademy.common.boxs.MovableBox;
import com.nhnacademy.common.boxs.PaintableBox;

import javafx.scene.canvas.GraphicsContext;

public class MixedWorld {
    private List<Ball> balls;
    private List<Box> boxes;
    private double width, height;

    public MixedWorld(double width, double height) {
        if(width <=0 || height <= 0) {
            throw new IllegalArgumentException("너비와 높이는 0보다 커야합니다.");
        }
        this.width = width;
        this.height = height;
    }
     
    public void addBall(Ball ball) {
        if(ball == null) {
            throw new IllegalArgumentException("ball은 null일 수 없습니다.");
        }
        balls.add(ball);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void addBox(Box box) {
        if(box == null) {
            throw new IllegalArgumentException("box는 null일 수 없습니다.");
        }
        boxes.add(box);
    }

    public int getBallCount() {
        return balls.size();
    }

    public int getBoxCount() {
        return boxes.size();
    }

    public void update(double deltaTime) {
        for(Ball ball : balls) {
            if(ball instanceof MovableBall) {
                ((MovableBall) ball).move(deltaTime);
            }
        }

        for(Box box : boxes) {
            if(box instanceof MovableBox) {
                ((MovableBox) box).move(deltaTime);
            }
        }
    }

    public void render(GraphicsContext gc) {
        for(Ball ball : balls) {
            if(ball instanceof PaintableBall) {
                ((PaintableBall) ball).paint(gc);
            }
        }

        for(Box box : boxes) {
            if(box instanceof PaintableBox) {
                ((PaintableBox) box).paint(gc);
            }
        }
    }
}
