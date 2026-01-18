package com.nhnacademy.breakout;

import com.nhnacademy.breakout.interfaces.MultiHit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MultiHitBrick extends SimpleBrick implements MultiHit {
    private int hitPoints;
    private int maxHitPoints;

    public MultiHitBrick(double x, double y, double width, double height, Color color, int maxHitPoints, int points) {
        super(x, y, width, height, color, points);
        this.maxHitPoints = maxHitPoints;
        this.hitPoints = maxHitPoints;
    }

    @Override
    public void hit(int damage) {
        hitPoints -= damage;
        if (hitPoints <= 0) {
            super.hit(damage);
        }
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    @Override
    public void paint(GraphicsContext gc) {
        super.paint(gc);

        // 깨어 있지 않고 데미지를 받은 상태면 균열 표시
        if (!isBroken() && hitPoints < maxHitPoints) {
            gc.setStroke(Color.BLACK);
            gc.strokeLine(
                getX() + 5,
                getY() + getHeight() / 2,
                getX() + getWidth() - 5,
                getY() + getHeight() / 2
            );
        }
    }
}
