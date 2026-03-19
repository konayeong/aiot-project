package com.nhnacademy.breakout;

import java.util.Random;

import com.nhnacademy.breakout.enums.PowerUpType;
import com.nhnacademy.breakout.interfaces.PowerUpProvider;

import javafx.scene.paint.Color;

public class PowerUpBrick extends SimpleBrick implements PowerUpProvider {
    private Random random = new Random();
    private final double dropChance; 

    public PowerUpBrick(double x, double y, double width, double height, Color color,
                        int points, double dropChance) {
        super(x, y, width, height, color, points);
        this.dropChance = dropChance;
    }

    @Override
    public boolean shouldDropPowerUp() {
        return random.nextDouble() < dropChance;
    }

    @Override
    public PowerUpType getPowerUpType() {
        PowerUpType[] types = PowerUpType.values();
        return types[random.nextInt(types.length)];
    }
}