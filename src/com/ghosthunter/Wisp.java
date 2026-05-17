package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

public class Wisp extends Entity {

    private final Group view;

    public Wisp(double x, double y) {
        super(x, y, 0.9, 1.1, 30);
        view = new Group();
        Circle mainBody = new Circle(0,0,24,Color.RED);
        
        Group leftEye = new Group();
        Circle leftEyeOuter = new Circle(-6,0,4.5,Color.ORANGE);
        Circle leftEyeInner = new Circle(-6, 0, 2.5, Color.YELLOW);
        leftEye.getChildren().addAll(leftEyeOuter, leftEyeInner);
        
        Group rightEye = new Group();
        Circle rightEyeOuter = new Circle(6, 0, 4.5, Color.ORANGE);
        Circle rightEyeInner = new Circle(6, 0, 2.5, Color.YELLOW);
        rightEye.getChildren().addAll(rightEyeOuter, rightEyeInner);


        Group rectsGroup = new Group();
        int numRectangles = 6;

        for (int i = 0; i < numRectangles; i++) {
            Rectangle ray = new Rectangle(-1, -40, 2, 12);
            ray.setFill(Color.RED);
            
            Rotate pivotRotation = new Rotate(i*(360.0 / numRectangles), 0, 0);
           
            ray.getTransforms().add(pivotRotation);
                    
            rectsGroup.getChildren().add(ray);
        }  
           

          

        RotateTransition rt = new RotateTransition(Duration.seconds(4), rectsGroup);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        view.getChildren().addAll(mainBody,leftEye, rightEye, rectsGroup);
        view.setEffect(new Glow(0.85));

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
        return 26;
    }
}	