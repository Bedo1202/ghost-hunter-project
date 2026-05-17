package com.ghosthunter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;
public class MainMenuView extends VBox {

    private final SceneManager sceneManager;

    public MainMenuView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // 1. DÜZEN AYARLARI
        // Görselde butonlar sol tarafta olduğu için hizalamayı sola aldık
        this.setAlignment(Pos.CENTER_LEFT); 
        this.setSpacing(15); // Butonlar arası boşluk
        this.setPadding(new Insets(100, 0, 0, 150)); // Butonları soldan biraz içeri ittik

        // 2. ARKA PLAN GÖRSELİ (menu_bg.png)
        try {
            Image bg = new Image(getClass().getResourceAsStream("Mainmenu.png"));
            BackgroundImage backgroundImage = new BackgroundImage(
                bg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
            );
            this.setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            // Eğer resim yüklenirken bir sorun çıkarsa oyunun çökmesini engeller ve düz mor renk boyar
            this.setBackground(new Background(new BackgroundFill(Color.web("#150B24"), null, null)));
        }

        // 3. BUTONLARIN OLUŞTURULMASI
        Button startBtn = createStyledButton("START GAME");
        Button levelBtn = createStyledButton("SELECT LEVEL");
        Button exitBtn = createStyledButton("EXIT");

        // 4. BUTON AKSİYONLARI
        startBtn.setOnAction(e -> sceneManager.startLevel(1));
        levelBtn.setOnAction(e -> sceneManager.showLevelSelection());
        exitBtn.setOnAction(e -> System.exit(0));

        this.getChildren().addAll(startBtn, levelBtn, exitBtn);
    }

    /**
     * PDF İsteri: Buton tıklandığında arka plan BEYAZ, yazı KIRMIZI olmalı.
     */
    private Button createStyledButton(String text) {
        Button btn = new Button(text);

        Font buttonFont = Font.loadFont(
                getClass().getResourceAsStream("Creepster-Regular.ttf"),
                24
        );

        if (buttonFont == null) {
            buttonFont = Font.font("Arial", 24);
        }

        btn.setFont(buttonFont);

        btn.setPrefSize(180, 40);

        Background defaultBg = new Background(new BackgroundFill(Color.web("#9400D3"), null, null));
        Background clickBg = new Background(new BackgroundFill(Color.WHITE, null, null));

        btn.setBackground(defaultBg);
        btn.setTextFill(Color.WHITE);

        btn.setOnMousePressed(e -> {
            btn.setBackground(clickBg);
            btn.setTextFill(Color.RED);
        });

        btn.setOnMouseReleased(e -> {
            btn.setBackground(defaultBg);
            btn.setTextFill(Color.WHITE);
        });

        return btn;
    }
}