package com.nhnacademy.breakout.interfaces;

public interface Breakable {
    void hit(int damage);
    boolean isBroken();
    int getPoints();
}
