package com.ghosthunter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Grup Projesi - LoseScreen Sınıfı
 * Amaç: PDF Sayfa 13'teki tamamen BEYAZ arka planlı, kırmızı yazılı Game Over ekranı ve buton animasyonları.
 */
public class LoseScreen extends VBox {

    public LoseScreen(SceneManager sceneManager, int currentLevel, int finalScore) {
        // 1. PANEL YERLEŞİM AYARLARI
        this.setAlignment(Pos.CENTER); // Tüm elemanları ekranın tam ortasına hizalar
        this.setSpacing(25); // Elemanların arasındaki dikey boşluk miktarı

        // 2. SAF BEYAZ ARKA PLAN (PDF Sayfa 13 İsteri)
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        // 3. "GAME OVER" BAŞLIĞI (Kırmızı ve Kalın)
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setFont(Font.font("Impact", FontWeight.BOLD, 52)); // PDF'teki heybetli font
        gameOverLabel.setTextFill(Color.RED);

        // 4. "FINAL SCORE" YAZISI (Kırmızı ve Kalın)
        Label scoreLabel = new Label("FINAL SCORE: " + finalScore);
        scoreLabel.setFont(Font.font("Impact", FontWeight.BOLD, 24));
        scoreLabel.setTextFill(Color.RED);

        // 5. BUTONLARIN YAN YANA DİZİLMESİ İÇİN HBOX
        HBox buttonLayout = new HBox(20); // Butonlar arası yatay boşluk
        buttonLayout.setAlignment(Pos.CENTER);

        // Butonların Oluşturulması
        Button retryBtn = createLoseButton("RETRY");
        Button menuBtn = createLoseButton("MAIN MENU");

        // Buton Aksiyonlarının SceneManager'a Bağlanması
        retryBtn.setOnAction(e -> sceneManager.startLevel(currentLevel)); // Aynı seviyeyi yeniden başlatır
        menuBtn.setOnAction(e -> sceneManager.showMainMenu()); // Ana menüye geri döner

        buttonLayout.getChildren().addAll(retryBtn, menuBtn);

        // Tüm katmanları ana dikey kutuya (VBox) ekliyoruz
        this.getChildren().addAll(gameOverLabel, scoreLabel, buttonLayout);
    }

    /**
     * PDF İsteri: Tıklandığında arka plan mordan beyaza, yazı kırmızıya döner.
     */
    private Button createLoseButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btn.setPrefSize(140, 42); // Dikdörtgen buton boyutları

        // Renk Tanımlamaları
        Background purpBg = new Background(new BackgroundFill(Color.web("#800080"), null, null)); // Varsayılan Mor
        Background clickBg = new Background(new BackgroundFill(Color.WHITE, null, null)); // Tıklanınca Beyaz

        // Butonun İlk Hali: Mor arka plan, Beyaz yazı
        btn.setBackground(purpBg);
        btn.setTextFill(Color.WHITE);

        // TIKLANMA ANI (Mouse Pressed): Arka plan BEYAZ, yazı KIRMIZI olur
        btn.setOnMousePressed(e -> {
            btn.setBackground(clickBg);
            btn.setTextFill(Color.RED);
        });

        // TIKLAMAYI BIRAKMA ANI (Mouse Released): Eski mor haline geri döner
        btn.setOnMouseReleased(e -> {
            btn.setBackground(purpBg);
            btn.setTextFill(Color.WHITE);
        });

        return btn;
    }
}