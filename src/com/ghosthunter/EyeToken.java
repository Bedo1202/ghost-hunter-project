package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class EyeToken extends Token {
    private final Ellipse view;
    private final int visibilitySuresi;

    public EyeToken(double x, double y, int visibilitySuresi) {
        super(x, y);
        this.visibilitySuresi = visibilitySuresi;

        
        view = new Ellipse(x, y, 16, 10);
        view.setFill(Color.LIGHTSKYBLUE);
        view.setStroke(Color.WHITE);
        view.setStrokeWidth(2);
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