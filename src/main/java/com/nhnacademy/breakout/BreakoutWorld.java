package com.nhnacademy.breakout;

import java.util.ArrayList;
import java.util.List;

import com.nhnacademy.breakout.common.Ball;
import com.nhnacademy.breakout.common.ExplosionEffect;
import com.nhnacademy.breakout.common.Vector2D;
import com.nhnacademy.breakout.interfaces.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BreakoutWorld {
    private final int width;
    private final int height;

    private BreakoutGame game;

    private List<UnbreakableBrick> walls = new ArrayList<>();
    private List<Breakable> bricks = new ArrayList<>();
    private List<Ball> balls = new ArrayList<>();
    private BreakoutPaddle paddle;
    private List<PowerUp> powerUps = new ArrayList<>();
    private List<ExplosionEffect> explosions = new ArrayList<>();
    private List<PowerUp> collectedPowerUps = new ArrayList<>();

    private int score = 0;

    public BreakoutWorld(int width, int height) {
        this.width = width;
        this.height = height;
        createWalls();
    }

    public void setGame(BreakoutGame game) {
        this.game = game;
    }

    // 벽 생성
    public void createWalls() {
        walls.clear();
        walls.add(new UnbreakableBrick(0, 0, width, 20));           // top
        walls.add(new UnbreakableBrick(0, 0, 20, height));          // left
        walls.add(new UnbreakableBrick(width - 20, 0, 20, height)); // right
    }

    // 레벨 생성
    public void createLevel(LevelConfig config) {
        bricks.clear();
        balls.clear();
        powerUps.clear();
        explosions.clear();

        int rows = config.getRows();
        int cols = config.getCols();

        LevelManager manager = new LevelManager();
        double brickWidth = (width - 40) / (double) cols; // 좌우 20씩 여유
        double brickHeight = 30;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = 20 + c * brickWidth;
                double y = 50 + r * brickHeight;
                Class<? extends Breakable> type = manager.selectBrickType(config);

                Breakable brick = null;
                if (type.equals(SimpleBrick.class)) {
                    brick = new SimpleBrick(x, y, brickWidth - 2, brickHeight - 2, Color.RED, 10);
                } else if (type.equals(MultiHitBrick.class)) {
                    brick = new MultiHitBrick(x, y, brickWidth - 2, brickHeight - 2, Color.ORANGE, 30, 3);
                } else if (type.equals(ExplodingBrick.class)) {
                    brick = new ExplodingBrick(x, y, brickWidth - 2, brickHeight - 2, Color.DARKRED, 20, y, c);
                } else if (type.equals(PowerUpBrick.class)) {
                    brick = new PowerUpBrick(x, y, brickWidth - 2, brickHeight - 2, Color.GREEN, 10, 1.0);
                }

                if (brick != null) bricks.add(brick);
            }
        }

        // 패들과 공 초기화
        if (paddle == null) createPaddle(width / 2 - 60, height - 60);
        if (balls.isEmpty()) createBall(paddle.getX() + paddle.getWidth() / 2, paddle.getY() - 30);
    }

    public void createPaddle(double x, double y) {
        paddle = new BreakoutPaddle(x, y, 120, 20, Color.BLUE, 600);
    }

    public void createBall(double x, double y) {
        // 공 중복 생성 방지: 공이 이미 존재하면 새 공 추가 안 함
        if (balls.isEmpty()) {
            Ball ball = new Ball(x, y, 10, Color.WHITE);
            ball.setVelocity(new Vector2D(200, -200));
            balls.add(ball);
        }
    }

    public void launchBall() {
        // 공이 하나도 없으면 새로 생성
        if (balls.isEmpty() && paddle != null) {
            createBall(paddle.getX() + paddle.getWidth() / 2, paddle.getY() - 30);
        }
    }

    // 게임 업데이트
    public void update(double dt) {
        if (paddle != null) paddle.update(dt);

        for (Ball b : balls) b.move(dt);
        for (PowerUp p : powerUps) p.move(dt);
        explosions.removeIf(ExplosionEffect::isFinished);

        handleCollisions();
        cleanup();
    }

    private void handleCollisions() {
        for (Ball ball : new ArrayList<>(balls)) {
            for (UnbreakableBrick wall : walls) {
                if (ball.collidesWith(wall)) ball.handleCollision(wall);
            }

            if (paddle != null && ball.collidesWith(paddle)) ball.handleCollision(paddle);

            for (Breakable brick : new ArrayList<>(bricks)) {
                if (brick instanceof Collidable c && ball.collidesWith(c)) {
                    ball.handleCollision(c);
                    brick.hit(1);

                    if (brick.isBroken()) handleBrickDestroyed(brick);
                }
            }
        }

        for (PowerUp p : new ArrayList<>(powerUps)) {
            if (p.collidesWith(paddle)) {
                p.collect();
                p.applyEffect(game);
                powerUps.remove(p);
            }
        }
    }

    private void handleBrickDestroyed(Breakable brick) {
        score += brick.getPoints();

        if (brick instanceof Exploding exploding && brick instanceof Collidable c) {
            explosions.add(new ExplosionEffect(
                exploding.getExplosionRadius(),
                exploding.getExplosionDamage(),
                c.getBounds().getCenterX(),
                c.getBounds().getCenterY()
            ));
        }

        if (brick instanceof PowerUpProvider provider && brick instanceof Collidable c) {
            if (provider.shouldDropPowerUp()) {
                powerUps.add(new PowerUp(
                    c.getBounds().getCenterX(),
                    c.getBounds().getCenterY(),
                    provider.getPowerUpType()
                ));
            }
        }
    }

    private void cleanup() {
        bricks.removeIf(Breakable::isBroken);
        powerUps.removeIf(PowerUp::isCollected);

        // 화면 밖으로 떨어진 공 처리
        List<Ball> fallenBalls = new ArrayList<>();
        for (Ball b : balls) {
            if (b.getY() > height) { // 바닥 아래로 떨어짐
                fallenBalls.add(b);
            }
        }

        for (Ball b : fallenBalls) {
            balls.remove(b); // 공 제거
            if (game != null) game.loseLife(); // 라이프 감소
        }
    }


    public void paint(GraphicsContext gc) {
        for (UnbreakableBrick w : walls) w.paint(gc);
        for (Breakable b : bricks) if (b instanceof Paintable p) p.paint(gc);
        for (PowerUp p : powerUps) p.paint(gc);
        if (paddle != null) paddle.paint(gc);
        for (Ball b : balls) b.paint(gc);
        for (ExplosionEffect e : explosions) e.paint(gc);
    }

    // Getters
    public BreakoutPaddle getPaddle() { return paddle; }
    public List<Ball> getBalls() { return balls; }
    public List<Breakable> getBricks() { return bricks; }
    public int getScore() { return score; }
    public List<PowerUp> getPowerUps() { return powerUps; }

    public List<PowerUp> getCollectedPowerUps() {
        List<PowerUp> temp = new ArrayList<>(collectedPowerUps);
        collectedPowerUps.clear(); // 한 번 처리 후 비움
        return temp;
    }

    
}
