package com.nhnacademy.world;

import java.util.ArrayList;
import java.util.List;

import com.nhnacademy.breakout.common.Box;
import com.nhnacademy.breakout.interfaces.Collidable;
import com.nhnacademy.breakout.interfaces.Movable;
import com.nhnacademy.breakout.interfaces.Paintable;
import com.nhnacademy.common.Point;
import com.nhnacademy.common.enums.CollisionAction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimpleWorld {
    private final double width;
    private final double height;

    private final List<Object> objects = new ArrayList<>();
    private final List<Box> boundaries = new ArrayList<>();

    public SimpleWorld(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("World 크기는 0보다 커야 합니다.");
        }

        this.width = width;
        this.height = height;

        createBoundaries();
    }

    public void createBoundaries() {
        double thickness = 10;

        Box top = new Box(new Point(0, -thickness), width, thickness);
        Box bottom = new Box(new Point(0, height), width, thickness);
        Box left = new Box(new Point(-thickness, 0), thickness, height);
        Box right = new Box(new Point(width, 0), thickness, height);

        top.setCollisionAction(CollisionAction.BOUNCE);
        bottom.setCollisionAction(CollisionAction.BOUNCE);
        left.setCollisionAction(CollisionAction.BOUNCE);
        right.setCollisionAction(CollisionAction.BOUNCE);

        boundaries.add(top);
        boundaries.add(bottom);
        boundaries.add(left);
        boundaries.add(right);
    }

    public void addObject(Object obj) {
        objects.add(obj);
    }

    public void update(double deltaTime) {
        for (Object obj : objects) {
            if (obj instanceof Movable movable) {
                movable.move(deltaTime);
            }
        }

        for (Object obj : objects) {
            if (!(obj instanceof Collidable coll)) continue;

            for (Box boundary : boundaries) {
                if (coll.isColliding(boundary)) {
                    coll.handleCollision(boundary);
                }
            }
        }

        int size = objects.size();
        for (int i = 0; i < size; i++) {
            Object a = objects.get(i);
            if (!(a instanceof Collidable collA)) continue;

            for (int j = i + 1; j < size; j++) {
                Object b = objects.get(j);
                if (!(b instanceof Collidable collB)) continue;

                if (collA.isColliding(collB)) {
                    collA.handleCollision(collB);
                    collB.handleCollision(collA);
                }
            }
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        for (Object obj : objects) {
            if (obj instanceof Paintable paintable) {
                paintable.paint(gc);
            }
        }

        // 경계 박스는 안보이게 처리 (원하면 paint 가능)
    }
    
}
