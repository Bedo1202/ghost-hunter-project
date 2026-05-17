package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

public class Wisp extends Entity {

    private final Group view;

    public Wisp(double x, double y) {
        super(x, y, 0.9, 1.1, 30);
        view = new Group();

        RadialGradient glow = new RadialGradient(
                0,
                0,
                0,
                0,
                25,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.YELLOW),
                new Stop(0.5, Color.ORANGE),
                new Stop(1, Color.TRANSPARENT)
        );

        Circle outerCircle = new Circle(0, 0, 25, glow);
        Circle innerCircle = new Circle(0, 0, 10, Color.RED);
        innerCircle.setOpacity(0.7);

        Group rectsGroup = new Group();

        for (int i = 0; i < 4; i++) {
            Rectangle rect = new Rectangle(-3, -30, 5, 10);
            rect.setFill(Color.ORANGERED);

            Group wrapper = new Group(rect);
            wrapper.setRotate(i * 90);

            rectsGroup.getChildren().add(wrapper);
        }

        RotateTransition rt = new RotateTransition(Duration.seconds(3), rectsGroup);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.play();

        view.getChildren().addAll(outerCircle, innerCircle, rectsGroup);
        view.setEffect(new Glow(0.80));

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
        return 25;
    }
}	