package com.ghosthunter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
  //Ali_Yüşa_Sezer_150124071
  //Ahmet_Kayra_Demirci_150124056
 
// GameHUD'da her seviyede olan sağda can, solda vakum ve orta üstte skorlan süreyi ayarladım
public class GameHUD {
    //Pano ayarlar
    private final BorderPane hudContainer;
    //Anlık olarak skor ve zamanı ekrana yansıtır
    private Label scoreL;
    private Label timerL;
    //Can ve vakum barlarının oyundaki duruma göre ekrana yansıtır
    private Rectangle healthF;
    private Rectangle vacuumF;
    //barların boyutları
    private final double barW = 34;
    private final double barH = 220;
    //HUD'u başlatır
    public GameHUD() {
    	//Panoyu düzenler
        hudContainer = new BorderPane();
        hudContainer.setPadding(new Insets(20));
        hudContainer.setPickOnBounds(false);

        initTCHUD();
        initLVHUD();
        initRHHUD();
    }

    //Skor ve süreyi ekranda ortaya ekliyor
    private void initTCHUD() {
    	//Orta panel ayarları
        VBox topB = new VBox(2);
        topB.setAlignment(Pos.CENTER);

        //Skor yazısını ayarlar
        scoreL = new Label("SCORE: 0");
        scoreL.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),22));
        scoreL.setTextFill(Color.WHITE);
        //Süre yazısını ayarlar
        timerL = new Label(""); 
        timerL.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),26));
        timerL.setTextFill(Color.WHITE);
        //Skor ve süreyi ekrana ayarlar
        topB.getChildren().addAll(scoreL, timerL);
        hudContainer.setTop(topB);
        BorderPane.setAlignment(topB, Pos.CENTER);
    }

    //Vakum barını ekranın soluna ayarlar
    private void initLVHUD() {
        //Sol panel ayarları
    	VBox leftB = new VBox(5);
        leftB.setAlignment(Pos.TOP_CENTER);
        
        //Vakum yazısını ayarlar
        Label vacuumTitle = new Label("VACUUM:");
        vacuumTitle.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),16));
        vacuumTitle.setTextFill(Color.WHITE);

        //Vakum barının dışındaki çerçeve ve ve çerçeve içini oluşturur
        Rectangle vacuumBg = new Rectangle(barW, barH -7);
        vacuumBg.setFill(Color.BLACK);
        vacuumBg.setStroke(Color.WHITE);
        vacuumBg.setStrokeWidth(3);
        vacuumBg.setArcWidth(12);
        vacuumBg.setArcHeight(12);

        //Barı oluşturur
        vacuumF = new Rectangle(barW -4, barH -4);
        vacuumF.setFill(Color.PURPLE);
        vacuumF.setArcWidth(8);
        vacuumF.setArcHeight(8);

        //Bar ve çerçeveyi birleştirir
        StackPane vacuumStack = new StackPane(vacuumBg, vacuumF);
        vacuumStack.setAlignment(Pos.BOTTOM_CENTER);
        vacuumStack.setPadding(new Insets(4));
        
        VBox barWrapper = new VBox(vacuumStack);
        barWrapper.setPadding(new Insets(10, 0, 0, 0));
        //Bar ve ismini ayarlar
        leftB.getChildren().addAll(vacuumTitle, barWrapper);
        hudContainer.setLeft(leftB);
    }

    //Can barını sağa ayarlar
    private void initRHHUD() {
    	//sağ panel ayarları
        VBox rightB = new VBox(5);//
        rightB.setAlignment(Pos.TOP_CENTER);

        //Health yazısını ayarlar
        Label healthTitle = new Label("HEALTH:");
        healthTitle.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),16));
        healthTitle.setTextFill(Color.WHITE);

        //Can barının dışındaki çerçeve ve çerçeve içini oluşturur
        Rectangle healthBg = new Rectangle(barW, barH -7);
        healthBg.setFill(Color.BLACK);
        healthBg.setStroke(Color.WHITE);
        healthBg.setStrokeWidth(3);
        healthBg.setArcWidth(12);
        healthBg.setArcHeight(12);

        //Barı oluşturur
        healthF = new Rectangle(barW -4, barH -4);
        healthF.setFill(Color.RED);
        healthF.setArcWidth(8);
        healthF.setArcHeight(8);
        //Bar ve çerçeveyi birleştirir
        StackPane healthStack = new StackPane(healthBg, healthF);
        healthStack.setAlignment(Pos.BOTTOM_CENTER);
        healthStack.setPadding(new Insets(4));

        VBox barWrapper = new VBox(healthStack);
        barWrapper.setPadding(new Insets(10, 0, 0, 0));
        
        rightB.getChildren().addAll(healthTitle, barWrapper);
        hudContainer.setRight(rightB);
    }
    //İletişimi sağlar
    public BorderPane getContainer() { 
    	return hudContainer; 
    }

    public void syncValues(int score, String timeStr, double hp, double vac) {
    	//Anlık skoru ayarlar
        scoreL.setText("SCORE: " + score);
        timerL.setText(timeStr);
        //Barları 0 1 arasında tutar
        hp = Math.max(0, Math.min(1, hp));
        vac = Math.max(0, Math.min(1, vac));
        //Barları anlık olarak ayarlar
        healthF.setHeight((barH - 8) * hp);
        vacuumF.setHeight((barH - 8) * vac);
    }
}