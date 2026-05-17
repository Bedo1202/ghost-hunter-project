package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.effect.DropShadow;
public class HealthToken extends Token {
	private final Group view;
    private final int healthIncrease;

    
    
    public HealthToken(double x, double y, int healthIncrease) {
        super(x, y);
        this.healthIncrease = healthIncrease;
        view = new Group();

        Circle base = new Circle(0, 0, 14);
        base.setFill(Color.YELLOW);
        base.setStroke(Color.BLACK);
        base.setStrokeWidth(2.5);

        Rectangle horiz = new Rectangle(-9, -2.5, 18, 5);
        horiz.setFill(Color.RED);

        Rectangle vert = new Rectangle(-2.5, -9, 5, 18);
        vert.setFill(Color.RED);

        view.getChildren().addAll(base, horiz, vert);

        view.setEffect(new DropShadow(4, Color.BLACK));

        view.setLayoutX(x);
        view.setLayoutY(y);
    }
    public int getHealthIncrease() {
        return healthIncrease;
    }
    @Override
    public Node getView() {
        return view;
    }
    @Override
    public double getCollisionRadius() {
        return 14;
    }
}