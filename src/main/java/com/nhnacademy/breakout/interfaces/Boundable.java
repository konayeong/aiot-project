package com.nhnacademy.breakout.interfaces;

import com.nhnacademy.common.bounds.Bounds;

public interface Boundable {
    Bounds getBounds();
    default boolean isColliding(Boundable other) {
        return this.getBounds().intersects(other.getBounds());
    }
}
