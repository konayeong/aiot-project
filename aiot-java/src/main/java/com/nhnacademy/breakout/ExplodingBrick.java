package com.nhnacademy.breakout;

import com.nhnacademy.breakout.interfaces.Exploding;

import javafx.scene.paint.Color;

public class ExplodingBrick extends SimpleBrick implements Exploding {
    private final double explosionRadius;
    private final int explosionDamage;

    public ExplodingBrick(double x, double y, double width, double height, Color color,
                          int points, double explosionRadius, int explosionDamage) {
        super(x, y, width, height, color, points);
        this.explosionRadius = explosionRadius;
        this.explosionDamage = explosionDamage;
    }

    @Override
    public void hit(int damage) {
        super.hit(damage);
    }

    @Override
    public double getExplosionRadius() {
        return explosionRadius;
    }

    @Override
    public int getExplosionDamage() {
        return explosionDamage;
    }
}