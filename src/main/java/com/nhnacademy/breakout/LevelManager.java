package com.nhnacademy.breakout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.nhnacademy.breakout.interfaces.Breakable;

public class LevelManager {
    private final List<LevelConfig> levels = new ArrayList<>();
    private int currentLevel = 0;

    public LevelManager() {
        initializeLevels();
    }

    private void initializeLevels() {
        LevelConfig level1 = new LevelConfig("Start", 5, 10, 1.0);
        level1.addBrickProbability(SimpleBrick.class, 0.9);
        level1.addBrickProbability(PowerUpBrick.class, 0.1);
        levels.add(level1);

        LevelConfig level2 = new LevelConfig("Challenge", 6, 10, 1.2);
        level2.addBrickProbability(SimpleBrick.class, 0.6);
        level2.addBrickProbability(MultiHitBrick.class, 0.3);
        level2.addBrickProbability(PowerUpBrick.class, 0.1);
        levels.add(level2);

        LevelConfig level3 = new LevelConfig("Explosion", 7, 11, 1.3);
        level3.addBrickProbability(SimpleBrick.class, 0.5);
        level3.addBrickProbability(MultiHitBrick.class, 0.2);
        level3.addBrickProbability(ExplodingBrick.class, 0.2);
        level3.addBrickProbability(PowerUpBrick.class, 0.1);
        levels.add(level3);
    }

    public Class<? extends Breakable> selectBrickType(LevelConfig config) {
        double random = Math.random();
        double cumulative = 0;
        for (Map.Entry<Class<? extends Breakable>, Double> entry : config.getBrickProbabilities().entrySet()) {
            cumulative += entry.getValue();
            if (random < cumulative) {
                return entry.getKey();
            }
        }
        return SimpleBrick.class;
    }

    public LevelConfig getLevelConfig(int index) {
        if (index < 0 || index >= levels.size()) {
            return levels.get(0);
        }
        return levels.get(index);
    }

    public int getLevelCount() { return levels.size(); }
}
