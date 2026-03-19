package com.nhnacademy;

import com.nhnacademy.breakout.common.Ball;
import com.nhnacademy.breakout.common.Vector2D;
import com.nhnacademy.common.Point;
import com.nhnacademy.common.boxs.MovableBox;
import com.nhnacademy.common.enums.CollisionAction;
import com.nhnacademy.world.SimpleWorld;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimpleWorldApp extends Application {
    private SimpleWorld world;
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) {
        world = new SimpleWorld(800, 600);
        world.createBoundaries();
        canvas = new Canvas(800, 600);

        // 다양한 객체 추가
        Ball ball = new Ball(new Point(400, 300), 20, Color.RED);
        ball.setVelocity(new Vector2D(150, 100));
        ball.setCollisionAction(CollisionAction.BOUNCE);
        world.addObject(ball);

        MovableBox box = new MovableBox(new Point(200, 200), 40, 30, Color.BLUE);
        box.setVelocity(new Vector2D(-80, 120));
        box.setCollisionAction(CollisionAction.BOUNCE);
        world.addObject(box);

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                world.update(deltaTime);

                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, 800, 600);
                world.render(gc);
            }
        };
        timer.start();

        Scene scene = new Scene(new Pane(canvas), 800, 600);
        primaryStage.setTitle("Simple World Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
