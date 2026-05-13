package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class HealthToken extends Token {
    private final Rectangle view;
    private final int healthIncrease;

    
    
    public HealthToken(double x, double y, int healthIncrease) {
        super(x, y);
        this.healthIncrease = healthIncrease;
        view = new Rectangle(x - 10, y - 10, 20, 20);
        view.setFill(Color.LIMEGREEN);
        view.setArcWidth(8);
        view.setArcHeight(8);
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