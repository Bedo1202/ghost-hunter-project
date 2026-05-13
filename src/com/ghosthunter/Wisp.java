package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Wisp extends Entity {

    private final Circle view;

    public Wisp(double x, double y) {
        super(x, y, 1.2, 1.4, 30);
        view = new Circle(x, y, 25);
        view.setFill(Color.LIGHTBLUE);
        view.setOpacity(0.6);
        view.setVisible(false);
    }

    @Override
    protected void updateViewPosition() {
        view.setCenterX(x);
        view.setCenterY(y);
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