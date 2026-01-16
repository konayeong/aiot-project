package com.nhnacademy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Random;

import com.nhnacademy.common.BoundedBall;
import com.nhnacademy.common.GameLoop;
import com.nhnacademy.common.Point;
import com.nhnacademy.common.Vector2D;
import com.nhnacademy.world.MovableWorld;

public class MovableWorldApp extends Application {
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    private MovableWorld world;
    private GameLoop gameLoop;
    private Random random = new Random();

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        world = new MovableWorld(WIDTH, HEIGHT);
        gameLoop = new GameLoop(world, canvas.getGraphicsContext2D());

        createMovingBalls(10);

        // Scene 생성
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        // Stage 설정
        stage.setTitle("My First JavaFX Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        gameLoop.start();

    }

    private void createMovingBalls(int count) {
        for (int i = 0; i < count; i++) {
            // count개의 랜덤 위치, 크기, 색상, 속도의 MovableBall 생성
            // 랜덤 위치: 가장자리에서 반지름만큼 떨어진 곳
            // 랜덤 크기: 10~30 픽셀
            // 랜덤 색상: RGB 각각 0~1
            // 랜덤 속도: -100~100 pixels/second
            double radius = 10 + random.nextDouble() * 20;
            double x = radius + (random.nextDouble() * WIDTH - 2 * radius);
            double y = radius + (random.nextDouble() * HEIGHT - 2 * radius);
            Color color = randomColor();
            double velocityX = -10 + random.nextDouble() * 20;
            double velocityY = -10 + random.nextDouble() * 20;

            try {
                BoundedBall ball = new BoundedBall(new Point(x, y), radius, color, new Vector2D(velocityX, velocityY));
                ball.setBounds(0, 0, world.getWidth(), world.getHeight());

                world.add(ball);
            } catch (IllegalArgumentException e) {
                i--; // 유효하지 않은 공이 생성된 경우 다시 시도
                String message = String.format("(%f, %f, %f), color: %s, velocity: (%f, %f)",
                        x, y, radius, color.toString(), velocityX, velocityY);
                System.err.println("Failed to add ball: " + message);
            }
        }
    }

    private Color randomColor() {
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    public static void main(String[] args) {
        launch(args);
    }
}