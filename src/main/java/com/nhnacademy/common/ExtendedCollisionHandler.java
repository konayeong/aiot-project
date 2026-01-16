package com.nhnacademy.common;

import java.util.List;

import com.nhnacademy.common.balls.Ball;
import com.nhnacademy.common.boxs.Box;
import com.nhnacademy.common.enums.CollisionSide;

//TODO 충돌 처리
public class ExtendedCollisionHandler {
    // Ball-Ball
    public void checkBallToBallCollisions(List<Ball> balls){

    }

    // Box-Box
    public void checkBoxToBoxCollisions(List<Box> boxes) {

    }


    // Ball-Box
    public void checkBallToBoxCollisions(List<Ball> balls, List<Box> boxes) {

    }

    // 충돌면 판단
    CollisionSide detectCollisionSide(double x, double y, Box box) {
        // 임시
        return CollisionSide.CORNER;
    } 

    


    
}
