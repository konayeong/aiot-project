package com.nhnacademy.breakout.demo;

import com.nhnacademy.breakout.BreakoutPaddle;
import com.nhnacademy.breakout.common.Ball;
import com.nhnacademy.breakout.common.Vector2D;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PaddleDemo extends Application {
    private BreakoutPaddle paddle;
    private Ball ball;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        paddle = new BreakoutPaddle(350, 550, 100, 20, Color.BLUE, 600);
        ball = new Ball(400.0, 300.0, 10.0, Color.RED);
        ball.setVelocity(new Vector2D(200, 200));

        canvas.setOnMouseMoved(e -> paddle.setTargetX(e.getX()));

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) { lastTime = now; return; }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                paddle.move(deltaTime);
                ball.move(deltaTime);

                // 패들과 충돌 시 반사
                if (ball.collidesWith(paddle)) {
                    double angle = paddle.calculateReflectionAngle(ball);
                    double speed = ball.getVelocity().magnitude();
                    ball.setVelocity(new Vector2D(
                        speed * Math.sin(angle),
                        -Math.abs(speed * Math.cos(angle))
                    ));
                }

                draw(gc);
            }
        };
        timer.start();

        stage.setScene(new Scene(new Pane(canvas)));
        stage.show();
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 600);
        paddle.paint(gc);
        ball.paint(gc);
    }
}
