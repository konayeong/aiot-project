package com.nhnacademy.common.interfaces;

import com.nhnacademy.common.CollisionAction;

public interface Collidable extends Boundable{
    void handleCollision(Collidable other);
    CollisionAction getCollisionAction();
}
