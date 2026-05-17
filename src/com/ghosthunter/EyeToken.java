package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.effect.DropShadow;

public class EyeToken extends Token {
	private final Group view;
    private final int visibilitySuresi;

    public EyeToken(double x, double y, int visibilitySuresi) {
        super(x, y);
        this.visibilitySuresi = visibilitySuresi;

        
        view = new Group();

        Circle base = new Circle(0, 0, 16);
        base.setFill(Color.YELLOW);
        base.setStroke(Color.BLACK);
        base.setStrokeWidth(2.5);

        Arc topWhite = new Arc(0, 4, 11, 9, 35, 110);
        topWhite.setType(ArcType.CHORD);
        topWhite.setFill(Color.WHITE);
        topWhite.setStroke(Color.BLACK);

        Arc bottomWhite = new Arc(0, -4, 11, 9, 215, 110);
        bottomWhite.setType(ArcType.CHORD);
        bottomWhite.setFill(Color.WHITE);
        bottomWhite.setStroke(Color.BLACK);

        Circle iris = new Circle(0, 0, 4.5, Color.web("#1E90FF"));
        Circle pupil = new Circle(0, 0, 2, Color.BLACK);

        view.getChildren().addAll(
                base,
                topWhite,
                bottomWhite,
                iris,
                pupil
        );

        view.setEffect(new DropShadow(4, Color.BLACK));

        view.setLayoutX(x);
        view.setLayoutY(y);
    }
    public int getVisibilitySuresi() {
        return visibilitySuresi;
    }
    @Override
    public Node getView() {
        return view;
    }
    @Override
    public double getCollisionRadius() {
        return 16;
    }
}