// package com.nhnacademy.breakout;

// import com.nhnacademy.common.balls.Ball;
// import com.nhnacademy.common.enums.CollisionAction;
// import com.nhnacademy.interfaces.Collidable;

// import javafx.scene.paint.Color;

// public class BreakoutBall extends Ball {
//     private final double speed;

//     public BreakoutBall(double x, double y) {
//         super(x, y, 10, Color.RED); // radius 10
//         this.speed = 400;
//         setDx(0);
//         setDy(0);
//         setCollisionAction(CollisionAction.BOUNCE);
//     }

//     public double getSpeed() {
//         return Math.sqrt(getDx() * getDx() + getDy() * getDy());
//     }

//     public boolean isMoving() {
//         return getDx() != 0 || getDy() != 0;
//     }

//     public void launch() {
//         if (!isMoving()) {
//             setDx(speed * 0.5);      // 초기 x 방향 속도
//             setDy(-speed * 0.866);   // 초기 y 방향 속도
//         }
//     }

//     @Override
//     public void move(double deltaTime) {
//         super.move(deltaTime);

//         // 아래쪽 화면 밖으로 나가면 멈춤
//         if (getY() - getRadius() > 600) {
//             setDx(0);
//             setDy(0);
//         }
//     }

//     @Override
//     public void handleCollision(Collidable other) {
//         double y = getY();
//         double dy = getDy();
//         double dx = getDx();
//         if (other instanceof UnbreakableBrick w) {
//             if(w.getWidth() > w.getHeight()){
//                 if(dy > 0 && y + w.getHeight() >= w.getY()){
//                     dy = -Math.abs(dy);
//                 }else if(dy<0 && y<=w.getY() + w.getHeight()){
//                     dy = Math.abs(dy);
//                 }else{
//                     dx = -dx;
//                 }
//             }
//         }
//     }


//     // 화면에 그리기
//     public void render(GraphicsContext gc) {
//         gc.setFill(getColor());
//         gc.fillOval(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
//         gc.setStroke(Color.BLACK);
//         gc.strokeOval(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
//     }

//     // 충돌 체크
//     public boolean collidesWith(Collidable other) {
//         if (other instanceof BreakoutPaddle p) {
//             return getX() + getRadius() > p.getX() &&
//                     getX() - getRadius() < p.getX() + p.getWidth() &&
//                     getY() + getRadius() > p.getY() &&
//                     getY() - getRadius() < p.getY() + p.getHeight();
//         } else if (other instanceof SimpleBrick sb) {
//             return getX() + getRadius() > sb.getX() &&
//                     getX() - getRadius() < sb.getX() + sb.getWidth() &&
//                     getY() + getRadius() > sb.getY() &&
//                     getY() - getRadius() < sb.getY() + sb.getHeight();
//         } else if (other instanceof UnbreakableBrick w) {
//             return getX() + getRadius() > w.getX() &&
//                     getX() - getRadius() < w.getX() + w.getWidth() &&
//                     getY() + getRadius() > w.getY() &&
//                     getY() - getRadius() < w.getY() + w.getHeight();
//         }
//         return false;
//     }
// }

