package com.nhnacademy.breakout.common;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExplosionEffect {
    private double x, y;
    private double radius;
    private double maxRadius;
    private double duration;
    private double elapsedTime;
    private boolean finished;

    public ExplosionEffect(double x, double y, double maxRadius, double duration) {
        this.x = x;
        this.y = y;
        this.maxRadius = maxRadius;
        this.duration = duration;
    }

    public void update(double deltaTime) {
        if (finished) return;
        elapsedTime += deltaTime;

        double progress = elapsedTime / duration;
        if (progress >= 1.0) {
            progress = 1.0;
            finished = true;
        }
        radius = maxRadius * progress;
    }

    public boolean isFinished() {
        return finished;
    }

    public void paint(GraphicsContext gc) {
        if (finished) return;

        double alpha = 1.0 - (elapsedTime / duration);
        gc.setFill(Color.rgb(255, 165, 0, alpha));
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        gc.setStroke(Color.rgb(255, 69, 0, alpha));
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}
