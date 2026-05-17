package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.effect.Glow;

public class Ghost extends Entity {

	private final Group view;

    public Ghost(double x, double y) {
        super(x, y, 1.0, 1.0, 10);

        
        view = new Group();

        Arc head = new Arc(0, -5, 18, 18, 0, 180);
        head.setType(ArcType.ROUND);
        head.setFill(Color.rgb(255, 255, 255, 0.55));

        Rectangle body = new Rectangle(-18, -5, 36, 20);
        body.setFill(Color.rgb(255, 255, 255, 0.55));

        Circle leftEyeWhite = new Circle(-6, -8, 4, Color.WHITE);
        Circle leftEyePupil = new Circle(-6, -8, 2,Color.BLACK);
        
        Circle rightEyeWhite = new Circle(6, -8, 4, Color.WHITE);
        Circle rightEyePupil = new Circle(6,-8,2, Color.BLACK);

        view.getChildren().addAll(head, body, leftEyeWhite, leftEyePupil, rightEyeWhite, rightEyePupil);
        view.setEffect(new Glow(0.4));

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