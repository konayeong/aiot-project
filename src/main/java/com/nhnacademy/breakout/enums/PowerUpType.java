package com.nhnacademy.breakout.enums;

import javafx.scene.paint.Color;

public enum PowerUpType {
    WIDER_PADDLE("W", Color.BLUE, 10.0),
    MULTI_BALL("M", Color.YELLOW, 0),
    EXTRA_LIFE("+1", Color.GREEN, 0),
    LASER("L", Color.RED, 10.0),
    SLOW_BALL("S", Color.CYAN, 15.0),
    STICKY_PADDLE("G", Color.PURPLE, 10.0);

    private final String symbol;
    private final Color color;
    private final double duration;

    // constructor, getters
    PowerUpType(String symbol, Color color, double duration) {
        this.symbol = symbol;
        this.color = color;
        this.duration = duration;
    }

    public String getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }

    public double getDuration() {
        return duration;
    }
}
