package com.ghosthunter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


//Ali_Yüşa_Sezer_150124071
public class LoseScreen extends VBox {

    public LoseScreen(SceneManager sceneManager, int currentLevel, int finalScore) {
        //Panel ayarları
        this.setAlignment(Pos.CENTER); 
        this.setSpacing(25); 

        //Arka plan
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        //Oyun bitti yazısı
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),50)); 
        gameOverLabel.setTextFill(Color.RED);

        //Skor yazısı
        Label scoreL = new Label("FINAL SCORE: " + finalScore);
        scoreL.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),30));
        scoreL.setTextFill(Color.RED);

        // Butonları yan yana dizer
        HBox buttonL = new HBox(20); 
        buttonL.setAlignment(Pos.CENTER);

        // Butonların yaratılması
        Button retryBtn = createLB("RETRY");
        Button menuBtn = createLB("MAIN MENU");

        // Butonları ScaneManager'e bağlar
        retryBtn.setOnAction(e -> sceneManager.startLevel(currentLevel)); 
        menuBtn.setOnAction(e -> sceneManager.showMainMenu()); 

        buttonL.getChildren().addAll(retryBtn, menuBtn);

        // Kutuya ekler
        this.getChildren().addAll(gameOverLabel, scoreL, buttonL);
    }

    //Butonları yaratır
    private Button createLB(String text) {
        //Buton özellikleri
    	Button b = new Button(text);
        b.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),25));
        b.setPrefSize(150, 50); 

        // Butonların renkleri
        Background purpB = new Background(new BackgroundFill(Color.PURPLE, null, null)); 
        Background clickB = new Background(new BackgroundFill(Color.WHITE, null, null)); 

        //Butonun anlık renk ayarları
        b.setBackground(purpB);
        b.setTextFill(Color.WHITE);

        b.setOnMousePressed(e -> {
            b.setBackground(clickB);
            b.setTextFill(Color.RED);
        }
        );

        b.setOnMouseReleased(e -> {
            b.setBackground(purpB);
            b.setTextFill(Color.WHITE);
        }
        );

        return b;
    }
}