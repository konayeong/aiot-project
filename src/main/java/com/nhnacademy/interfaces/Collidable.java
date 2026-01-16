package com.nhnacademy.interfaces;

import com.nhnacademy.common.enums.CollisionAction;

public interface Collidable extends Boundable{
    void handleCollision(Collidable other);
    CollisionAction getCollisionAction();
}
