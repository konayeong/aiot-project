package com.nhnacademy.common.boxs;

import com.nhnacademy.common.Point;
import com.nhnacademy.common.bounds.RectangleBounds;
import com.nhnacademy.interfaces.Paintable;

import javafx.scene.canvas.GraphicsContext;

// 경계나 장애물로 사용
public class Box implements Paintable{
    private Point position;
    private double width;
    private double height;
    
    public Box(Point point, double width, double height) {
        if(point == null) {
            throw new IllegalArgumentException("Point는 null일 수 없습니다.");
        }
        if(width <=0 || height <= 0) {
            throw new IllegalArgumentException("너비, 높이는 0보다 커야 합니다.");
        }
        
        position = point;
        this.width = width;
        this.height = height;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean contains(Point point) {
        double boxX = position.getX();
        double boxY = position.getY();

        return (point.getX() >= boxX && point.getX() <= boxX + width
                && point.getY() >= boxY && point.getY() <= boxY + height);
    }

    public RectangleBounds getBounds() {
        return new RectangleBounds(position.getX(), position.getY(), width, height);
    }

    @Override
    public void paint(GraphicsContext gc) {
        // gc.setFill(color); // NOTE color를 어디서 구하는데
        gc.fillRect(position.getX(), position.getY(), width, height);
    }
}
