package com.nhnacademy.breakout;

import java.util.ArrayList;
import java.util.List;

import com.nhnacademy.breakout.common.Ball;
import com.nhnacademy.breakout.common.Vector2D;
import com.nhnacademy.breakout.enums.GameState;
import com.nhnacademy.common.Point;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class BreakoutGame extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int INITIAL_LIVES = 3;

    private BreakoutWorld world;
    private LevelManager levelManager;
    private GameState gameState = GameState.MENU;
    private int score = 0;
    private int lives = INITIAL_LIVES;
    private int level = 1;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        levelManager = new LevelManager();
        initializeGame();

        canvas.setOnMouseMoved(this::handleMouseMove);
        canvas.setOnMouseClicked(this::handleMouseClick);
        Scene scene = new Scene(new Pane(canvas));
        scene.setOnKeyPressed(this::handleKeyPress);

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) { lastTime = now; return; }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                if (deltaTime > 0.05) deltaTime = 0.05;

                updateGame(deltaTime);
                render(gc);
            }
        };
        gameLoop.start();

        stage.setTitle("Breakout Game");
        stage.setScene(scene);
        stage.show();
    }

    private void initializeGame() {
        world = new BreakoutWorld(WIDTH, HEIGHT);
        world.setGame(this);
        level = 1;
        lives = INITIAL_LIVES;
        score = 0;
        gameState = GameState.MENU;
        loadLevel(level);
    }

    private void loadLevel(int levelNumber) {
        LevelConfig config = levelManager.getLevelConfig(levelNumber - 1);
        world.createLevel(config);
        // 패들과 공 초기화
        world.createPaddle(WIDTH / 2 - 50, HEIGHT - 50);
        world.createBall(WIDTH / 2, HEIGHT - 100);
    }

    private void updateGame(double deltaTime) {
        if (gameState != GameState.PLAYING) return;

        world.update(deltaTime);
        score = world.getScore();

        checkGameConditions();
        checkPowerUps();
    }

    private void checkPowerUps() {
        for (PowerUp p : world.getCollectedPowerUps()) {
            p.applyEffect(this); // 라이프 증가, 멀티볼 등 적용
        }
    }

    private void checkGameConditions() {
        // 공이 하나도 없으면 라이프 감소
        if (world.getBalls().isEmpty()) {
            lives--;
            if (lives <= 0) {
                gameState = GameState.GAME_OVER;
                return;
            }
            // 공 다시 생성
            world.createBall(WIDTH / 2, HEIGHT - 100);
            return;
        }

        // 벽돌 모두 제거되면 레벨 완료
        if (world.getBricks().isEmpty()) {
            level++;
            if (level > levelManager.getLevelCount()) {
                gameState = GameState.GAME_OVER;
            } else {
                gameState = GameState.LEVEL_COMPLETE;
            }
        }
    }

    private void handleMouseMove(MouseEvent e) {
        if (world.getPaddle() != null && (gameState == GameState.PLAYING || gameState == GameState.LEVEL_COMPLETE || gameState == GameState.MENU)) {
            world.getPaddle().setTargetX(e.getX());
        }
    }

    private void handleMouseClick(MouseEvent e) {
        if (gameState == GameState.PLAYING) {
            world.launchBall(); // 공 발사
        } else if (gameState == GameState.MENU) {
            startLevel();
        } else if (gameState == GameState.LEVEL_COMPLETE) {
            startLevel();
        }
    }

    private void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case SPACE:
                if (gameState == GameState.MENU || gameState == GameState.LEVEL_COMPLETE) {
                    startLevel();
                }
                break;
            case P:
                if (gameState == GameState.PLAYING) gameState = GameState.PAUSED;
                else if (gameState == GameState.PAUSED) gameState = GameState.PLAYING;
                break;
            case R:
                if (gameState == GameState.GAME_OVER) {
                    level = 1;
                    initializeGame();
                }
                break;
        }
    }

    private void startLevel() {
        loadLevel(level);
        gameState = GameState.PLAYING;
    }

    private void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        world.paint(gc);
        renderUI(gc);
    }

    private void renderUI(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        gc.fillText("Score: " + score, 10, 20);
        gc.fillText("Lives: " + lives, WIDTH - 80, 20);
        gc.fillText("Level: " + level, WIDTH / 2 - 30, 20);

        gc.setTextAlign(TextAlignment.CENTER);
        if (gameState == GameState.MENU) {
            gc.setFont(Font.font(32));
            gc.fillText("BREAKOUT", WIDTH / 2, HEIGHT / 2 - 50);
            gc.setFont(Font.font(18));
            gc.fillText("Press SPACE to Start", WIDTH / 2, HEIGHT / 2 + 20);
        } else if (gameState == GameState.PAUSED) {
            gc.setFont(Font.font(32));
            gc.fillText("PAUSED", WIDTH / 2, HEIGHT / 2);
        } else if (gameState == GameState.GAME_OVER) {
            gc.setFont(Font.font(32));
            gc.fillText("GAME OVER", WIDTH / 2, HEIGHT / 2 - 20);
            gc.setFont(Font.font(18));
            gc.fillText("Final Score: " + score, WIDTH / 2, HEIGHT / 2 + 20);
            gc.fillText("Press R to Restart", WIDTH / 2, HEIGHT / 2 + 50);
        }
    }

    // 멀티볼
    public void createMultiBalls() {
        List<Ball> currentBalls = world.getBalls();
        if (currentBalls.isEmpty()) return;

        Ball source = currentBalls.get(0);
        Point center = source.getCenter();
        double radius = source.getRadius();
        javafx.scene.paint.Color color = source.getColor();
        double speed = source.getVelocity().magnitude();

        List<Ball> newBalls = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Ball nextBall = new Ball(new Point(center.getX(), center.getY()), radius, color);
            double angle = Math.random() * Math.PI - Math.PI / 2;
            nextBall.setVelocity(new Vector2D(speed * Math.sin(angle), -Math.abs(speed * Math.cos(angle))));
            newBalls.add(nextBall);
        }

        currentBalls.addAll(newBalls);
    }

    // 라이프 증가
    public void addLife() { lives++; }

    public BreakoutPaddle getPaddle() { return world.getPaddle(); }

    public void loseLife() {
        lives--;
        if (lives <= 0) {
            gameState = GameState.GAME_OVER;
        } else {
            if (world.getPaddle() != null) {
                world.createBall(world.getPaddle().getX() + world.getPaddle().getWidth() / 2,
                                world.getPaddle().getY() - 30);
            }
        }
    }
}
