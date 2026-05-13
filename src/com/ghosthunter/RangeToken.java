package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RangeToken extends Token {
    private final Circle view;
    private final int rangeIncrease;
    public RangeToken(double x, double y, int rangeIncrease) {
        super(x, y);
        this.rangeIncrease = rangeIncrease;
        view = new Circle(x, y, 13);
        view.setFill(Color.GOLD);
    }
    public int getRangeIncrease() {
        return rangeIncrease;
    }
    @Override
    public Node getView() {
        return view;
    }
    @Override
    public double getCollisionRadius() {
        return view.getRadius();
    }
}