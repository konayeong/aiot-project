package com.nhnacademy.common.boxs;

import com.nhnacademy.common.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PaintableBox extends Box{
    private Color color;

    public PaintableBox(Point point, double width, double height) {
        this(point, width, height, Color.RED);
    }

    public PaintableBox(Point point, double width, double height, Color color) {
        super(point, width, height);
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if(color == null) {
            throw new IllegalArgumentException("color는 null이 될 수 없습니다.");
        }
        this.color = color;
    }

    public void paint(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(getPosition().getX(), getPosition().getY(), getWidth(), getHeight());
    }
}
