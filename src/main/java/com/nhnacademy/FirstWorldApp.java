package com.nhnacademy;

import com.nhnacademy.common.PaintableBall;
import com.nhnacademy.world.World;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FirstWorldApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 캔버스 생성
        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PINK, Color.ORANGE, Color.CYAN,
                Color.MAGENTA };

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        World world = new World(800, 600);
        for (int i = 0; i < 10; i++) {
            double x = Math.random() * 800;
            double y = Math.random() * 600;
            double radius = Math.random() * 50 + 10;
            Color color = colors[i % colors.length];
            try {
                world.add(new PaintableBall(x, y, radius, color));
            } catch (IllegalArgumentException e) {
                System.out.println("Ball is out of world bounds");
            }
        }

        world.draw(gc);

        // Scene 생성
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        // Stage 설정
        primaryStage.setTitle("My First Ball World");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}