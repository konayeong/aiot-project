package com.nhnacademy.interfaces;

import com.nhnacademy.common.bounds.Bounds;

public interface Boundable {
    Bounds getBounds();
    boolean isColliding(Boundable other);
}
