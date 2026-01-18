package com.nhnacademy.breakout.interfaces;

import com.nhnacademy.breakout.enums.PowerUpType;

// 파워업 제공
public interface PowerUpProvider {
    boolean shouldDropPowerUp();
    PowerUpType getPowerUpType();
}
