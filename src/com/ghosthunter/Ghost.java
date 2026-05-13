package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ghost extends Entity {

    private final Circle view;

    public Ghost(double x, double y) {
        super(x, y, 1.5, 1.5, 10);

        // şimdilik basit görünüm
        view = new Circle(x, y, 15);
        view.setFill(Color.WHITE);
        view.setOpacity(0.7);
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