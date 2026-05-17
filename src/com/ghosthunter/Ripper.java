package com.ghosthunter;
//Bedirhan_Acarturk_150124052
//Ahmet_Kayra_Demirci_150124056

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.effect.Glow;
import javafx.scene.transform.Rotate;

	//Entity sınıfının kalıtımı aktarıldı.
public class Ripper extends Entity {

    private final Group view;

    public Ripper(double x, double y) {
    	
    super(x, y, 1.3, 1.1, 20);

    // temsili görünüm
    view = new Group();
    Group spikesGroup = new Group();
 //12 adet diken ve elips göünümü için farklı yarıçaplar tanımlanır.
    int numSpikes = 12;
    double radiusOuterX =45.0;
    double radiusOuterY = 32.0;
    double radiusInnerX = 26.0;
    double radiusInnerY = 18.0;
    
    double baseHalfWidthAngle = 15.0;

    for (int i = 0; i < numSpikes; i++) {
    	//360 dereceyi diken sayısına bölerekher dikenein merkez açısını bulduk.
       double angleCenter = i* (360.0/ numSpikes);
       double angleLeft = angleCenter - baseHalfWidthAngle;
       double angleRight = angleCenter +baseHalfWidthAngle;
       
       //dikenler uç noktaları
       double x1 = radiusOuterX * Math.cos(Math.toRadians(angleCenter));
       double y1 = radiusOuterY* Math.sin(Math.toRadians(angleCenter));
       
       //dikenlerin tabanlarının sağ ve solayakları.
       double x2 = radiusInnerX * Math.cos(Math.toRadians(angleLeft));
       double y2 = radiusInnerY * Math.sin(Math.toRadians(angleLeft));
       
       double x3 = radiusInnerX * Math.cos(Math.toRadians(angleRight));
       double y3 = radiusInnerY * Math.sin(Math.toRadians(angleRight));
        Polygon spike = new Polygon();
        spike.getPoints().addAll(
                x1, y1,
                x2, y2,
                x3,y3
        );
        
        //döngü her çalıştığında bri spike oluşuyor.
        spike.setFill(Color.PURPLE);
        spikesGroup.getChildren().add(spike);
      
    }
    //şekli oluşturabilmek için mor bir elips, üstüne siyah bir merkez daire ve iki kırmızı göz yerleştirdik.
    Ellipse backgroundEllipse = new Ellipse(0.0,0.0, 26.0, 18.0);
    backgroundEllipse.setFill(Color.PURPLE);
    Circle coreCircle = new Circle(0.0,0.0,16.0,Color.BLACK);
    
    Circle solGöz = new Circle(-5, -2, 2.5, Color.RED);
    Circle sağGöz = new Circle(5, -2, 2.5, Color.RED);
    
    //tüm parçalar view grubuna eklendi.
    view.getChildren().addAll(spikesGroup, backgroundEllipse, coreCircle, solGöz, sağGöz);
    view.setEffect(new Glow(0.60));
    
    //başlangıçta görünmez olmasını sağladık.
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
    
    //ripper ve hunterın çakıştığını kontrol etmek içinripper yarıçapı 24 e ayarlandı.
    @Override
    public double getCollisionRadius() {
    return 24;
    }
}