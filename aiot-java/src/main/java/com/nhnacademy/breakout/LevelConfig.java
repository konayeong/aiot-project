package com.nhnacademy.breakout;

import java.util.HashMap;
import java.util.Map;
import com.nhnacademy.breakout.interfaces.Breakable;

public class LevelConfig {
    private String name;
    private int rows;
    private int cols;
    private double speedMultiplier;  // 공 속도 곱셈
    private Map<Class<? extends Breakable>, Double> brickProbabilities = new HashMap<>();

    // 배치 관련 기본값
    private int brickWidth = 70;
    private int brickHeight = 25;
    private int offsetX = 50;
    private int offsetY = 80;

    public LevelConfig(String name, int rows, int cols, double speedMultiplier) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.speedMultiplier = speedMultiplier;
    }

    public void addBrickProbability(Class<? extends Breakable> type, double prob) {
        brickProbabilities.put(type, prob);
    }

    public Map<Class<? extends Breakable>, Double> getBrickProbabilities() {
        return brickProbabilities;
    }

    // Getters
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getBrickWidth() { return brickWidth; }
    public int getBrickHeight() { return brickHeight; }
    public int getOffsetX() { return offsetX; }
    public int getOffsetY() { return offsetY; }
    public double getSpeedMultiplier() { return speedMultiplier; }
}
