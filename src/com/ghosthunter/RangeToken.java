package com.ghosthunter;
//Ahmet Kayra Demirci/150124056
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.effect.DropShadow;
//tokenın alt classı olarak range token
public class RangeToken extends Token {
    private Group view;
    private int rangeIncrease;
    public RangeToken(double x, double y, int rangeIncrease) {
        super(x, y);
        this.rangeIncrease = rangeIncrease;
        view = new Group();
        //token görünümü
        Circle base = new Circle(0, 0, 13);
        base.setFill(Color.YELLOW);
        base.setStroke(Color.BLACK);
        base.setStrokeWidth(2.5);
        //içindeki ok
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(8.0, 0.0,-5.0, -7.0,-5.0, 7.0);
        arrow.setFill(Color.BLACK);
        view.getChildren().addAll(base, arrow);
        view.setEffect(new DropShadow(4, Color.BLACK));
        view.setLayoutX(x);
        view.setLayoutY(y);
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
        return 13;
    }
}