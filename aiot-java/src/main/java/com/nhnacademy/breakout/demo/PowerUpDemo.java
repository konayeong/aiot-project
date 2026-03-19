package com.nhnacademy.breakout.demo;

import java.util.ArrayList;
import java.util.List;

import com.nhnacademy.breakout.BreakoutPaddle;
import com.nhnacademy.breakout.PowerUp;
import com.nhnacademy.breakout.enums.PowerUpType;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PowerUpDemo extends Application {
    private List<PowerUp> powerUps = new ArrayList<>();
    private BreakoutPaddle paddle;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        paddle = new BreakoutPaddle(350, 550, 100, 20, Color.BLUE, 600);

        // 다양한 파워업 생성
        powerUps.add(new PowerUp(100, 0, PowerUpType.WIDER_PADDLE));
        powerUps.add(new PowerUp(300, 0, PowerUpType.MULTI_BALL));
        powerUps.add(new PowerUp(500, 0, PowerUpType.EXTRA_LIFE));
        powerUps.add(new PowerUp(700, 0, PowerUpType.SLOW_BALL));

        canvas.setOnMouseMoved(e -> paddle.setTargetX(e.getX()));

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) { lastTime = now; return; }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                paddle.move(deltaTime);

                for (PowerUp p : powerUps) {
                    p.move(deltaTime);
                    if (p.collidesWith(paddle) && !p.isCollected()) {
                        p.collect();
                        System.out.println("Collected: " + p.getType());
                    }
                }

                draw(gc);
            }
        };
        timer.start();

        stage.setScene(new Scene(new Pane(canvas)));
        stage.show();
    }
    
    private void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);

        // 패들 그리기
        paddle.paint(gc);

        // 파워업 그리기
        for (PowerUp p : powerUps) {
            p.paint(gc);
        }
    }

}
