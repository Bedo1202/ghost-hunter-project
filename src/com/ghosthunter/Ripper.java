package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ripper extends Entity {

    private final Circle view;

    public Ripper(double x, double y) {
    super(x, y, 2.0, 1.7, 20);

    // temsili görünüm
    view = new Circle(x, y, 18);
    view.setFill(Color.PURPLE);
    view.setOpacity(0.8);
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