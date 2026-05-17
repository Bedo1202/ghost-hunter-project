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
//Entity classından kalıtım alınır.
public class Wisp extends Entity {

    private final Group view;
    public Wisp(double x, double y) {
        super(x, y, 0.9, 1.1, 30);
        
        
        
        view = new Group();
        //kırmı renkte ana gövde
        Circle mainBody = new Circle(0,0,24,Color.RED);
        
        //iç içe halkalardan oluşan sol göz
        Group solGöz = new Group();
        Circle solGözDış = new Circle(-6,0,4.5,Color.ORANGE);
        Circle solGözIç = new Circle(-6, 0, 2.5, Color.YELLOW);
        solGöz.getChildren().addAll(solGözDış, solGözIç);
        // içe halkalardan oluşan sağ göz
        Group sağGöz = new Group();
        Circle sağGözDış = new Circle(6, 0, 4.5, Color.ORANGE);
        Circle sağGözIç = new Circle(6, 0, 2.5, Color.YELLOW);
        sağGöz.getChildren().addAll(sağGözDış, sağGözIç);

// 6 tane dikdörtgen ışın yaptık.
        Group rectsGroup = new Group();
        int numRectangles = 6;

        for (int i = 0; i < numRectangles; i++) {
            Rectangle ray = new Rectangle(-1, -40, 2, 12);
            ray.setFill(Color.RED);
         //Rotate sınıfı ile her ışın (0,0) etrafında 60 derece döner ve sabitlenir.   
            Rotate pivotRotation = new Rotate(i*(360.0 / numRectangles), 0, 0);
           
            ray.getTransforms().add(pivotRotation);
                    
            rectsGroup.getChildren().add(ray);
        }  
           

          
        //yukarıdaki  dönüş hareketinin sonsuz tekrarlanmasını sağlar
        
        RotateTransition rt = new RotateTransition(Duration.seconds(4), rectsGroup);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
// tüm değişkenler view grubuna eklenir.
        view.getChildren().addAll(mainBody,solGöz, sağGöz, rectsGroup);
        view.setEffect(new Glow(0.85));
        
        
        //başlangıçta wispin görünmez olmasını sağladık.
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

    
    //wisp ve hunterın çakıştığını anlamak için wispin yarıçapını 26 yaptık.
    @Override
    public double getCollisionRadius() {
        return 26;
    }
}