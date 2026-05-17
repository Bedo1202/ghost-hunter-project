package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.effect.Glow;

public class Ripper extends Entity {

    private final Group view;

    public Ripper(double x, double y) {
    super(x, y, 1.3, 1.1, 20);

    // temsili görünüm
    view = new Group();
    Group spikesGroup = new Group();

    int numSpikes = 12;

    for (int i = 0; i < numSpikes; i++) {
        double angle = i * (360.0 / numSpikes);

        Polygon spike = new Polygon();
        spike.getPoints().addAll(
                0.0, -22.0,
                -5.0, -12.0,
                5.0, -12.0
        );

        spike.setFill(Color.PURPLE);
        spike.setRotate(angle);

        spikesGroup.getChildren().add(spike);
    }

    Circle core = new Circle(0, 0, 13, Color.BLACK);
    Circle leftEye = new Circle(-4, -2, 2.5, Color.RED);
    Circle rightEye = new Circle(4, -2, 2.5, Color.RED);

    view.getChildren().addAll(spikesGroup, core, leftEye, rightEye);
    view.setEffect(new Glow(0.60));

    updateViewPosition();
    view.setVisible(false);
    }

    
    @Override
    protected void updateViewPosition() {
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    @Override
    public Node getView() {
    return view;
    }

    @Override
    public double getCollisionRadius() {
    return 18;
    }
}