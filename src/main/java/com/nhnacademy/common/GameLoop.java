package com.nhnacademy.common;

import com.nhnacademy.world.MovableWorld;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop extends AnimationTimer {
    MovableWorld world;
    GraphicsContext gc;

    public GameLoop(MovableWorld world, GraphicsContext gc) {
        this.world = world;
        this.gc = gc;
    }

    @Override
    public void handle(long now) {
        world.update();
        world.draw(gc);
    }
}