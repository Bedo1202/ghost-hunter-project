package com.ghosthunter;
//Bedirhan_Acarturk_150124052
//Ahmet_Kayra_Demirci_150124056 

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
//üst sınıfdaki entity çağrılır ve constructorı kulanılır.
    public Ghost(double x, double y) {
        super(x, y, 1.0, 1.0, 10);

        
        view = new Group();
        //hayalet karakterininüst yani kafa kısmı, yarım daire şeklinde.renk ayarının sonundaki 0.55 değeri saydamlık için
        Arc baş = new Arc(0, -5, 18, 18, 0, 180);
        baş.setType(ArcType.ROUND);
        baş.setFill(Color.rgb(255, 255, 255, 0.55));
        //hayalet karakterinin gövde kısmı dikdirtgen şeklinde.renk ayarının sonundaki 0.55 değeri saydamlık için
        Rectangle body = new Rectangle(-18, -5, 36, 20);
        body.setFill(Color.rgb(255, 255, 255, 0.55));

        //gözler için daire kullandık, ve view grubunda birleştirdik.
        Circle leftEyeWhite = new Circle(-6, -8, 4, Color.WHITE);
        Circle leftEyePupil = new Circle(-6, -8, 2,Color.BLACK);
        
        Circle rightEyeWhite = new Circle(6, -8, 4, Color.WHITE);
        Circle rightEyePupil = new Circle(6,-8,2, Color.BLACK);

        view.getChildren().addAll(baş, body, leftEyeWhite, leftEyePupil, rightEyeWhite, rightEyePupil);
        view.setEffect(new Glow(0.4));
        
        //hayaletin oyunun başında görünmez olmasını sağlar.
        updateViewPosition();
        view.setVisible(false);
    }
    
    //değişen x ve y değerlerini view grubuna anlık gönderir.
    @Override
    protected void updateViewPosition() {
    view.setLayoutX(x);
    view.setLayoutY(y);
    }
    
    //GameController sınıfı ekrana hayalet(ler) yansıtmak istediğinde çağırır.
    @Override
    public Node getView() {
    return view;
    }

    //hayalet ile hunterın çakışıp çakışmadığını kontrol etmek için hayaletin yarıçapını 18birime ayarlar.
    @Override
    public double getCollisionRadius() {
    return 18;
    }
}