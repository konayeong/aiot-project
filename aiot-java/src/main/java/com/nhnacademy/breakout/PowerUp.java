package com.nhnacademy.breakout;

import com.nhnacademy.breakout.common.Ball;
import com.nhnacademy.breakout.common.Vector2D;
import com.nhnacademy.breakout.enums.PowerUpType;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PowerUp extends Ball {
    private PowerUpType type;
    private boolean collected = false;
    private static final double FALL_SPEED = 100;

    public PowerUp(double x, double y, PowerUpType type) {
        super(x, y, 15, type.getColor());
        this.type = type;
        setVelocity(new Vector2D(0, FALL_SPEED));
    }

    @Override
    public void paint(GraphicsContext gc) {
        if (collected) return;

        // 배경 원
        gc.setFill(type.getColor());
        gc.fillOval(getX() - getRadius(), getY() - getRadius(),
                    getRadius() * 2, getRadius() * 2);

        // 심볼
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(14));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(type.getSymbol(), getX(), getY() + 5);
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    public PowerUpType getType() {
        return type;
    }
    
    public void applyEffect(BreakoutGame game) {
        collected = true;
        switch (type) {
            case WIDER_PADDLE:
                game.getPaddle().expand(1.5, type.getDuration());
                break;
            case MULTI_BALL:
                game.createMultiBalls();
                break;
            case EXTRA_LIFE:
                game.addLife();
                break;
            // ...
        }
    }
}
