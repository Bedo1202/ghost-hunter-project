package com.ghosthunter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Grup Projesi - WinScreen Sınıfı
 * Amaç: PDF Sayfa 12'deki yarı saydam "YOU WIN!" panelinin ve buton animasyonunun oluşturulması.
 */
public class WinScreen extends VBox {

    public WinScreen(SceneManager sceneManager, int currentLevel) {
        // 1. PANEL YERLEŞİM AYARLARI
        this.setAlignment(Pos.CENTER); // Tüm yazıları ve butonu panelin tam ortasına hizalar
        this.setSpacing(15); // Elemanların arasındaki dikey boşluk

        // 2. YARI SAYDAM SİYAH ARKA PLAN (PDF İsteri)
        // Color.rgb(0, 0, 0, 0.75) -> Sondaki 0.75 değeri %75 opaklık sağlar. 
        // Böylece arkadaki oyun haritası hafif kararmış şekilde alttan görünür.
        this.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.75), null, null)));

        // 3. "YOU WIN!" BAŞLIĞI
        Label mainLabel = new Label("YOU WIN!");
        mainLabel.setFont(Font.font("Impact", FontWeight.BOLD, 44)); // Görseldeki kalın font stili
        mainLabel.setTextFill(Color.WHITE);

        // 4. ALTTARAFINDAKİ BİLGİLENDİRME METNİ
        Label subLabel = new Label("YOU'VE VACUUMED ALL THE GHOSTS!");
        subLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        subLabel.setTextFill(Color.WHITE);

        // 5. "NEXT LEVEL" BUTONU VE TIKLAMA RENK DEĞİŞİMİ
        Button nextLevelBtn = new Button("NEXT LEVEL");
        nextLevelBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nextLevelBtn.setPrefSize(160, 40); // Dikdörtgen buton boyutları

        // Renk Tanımlamaları
        Background purpBg = new Background(new BackgroundFill(Color.web("#800080"), null, null)); // Normal Mor
        Background clickBg = new Background(new BackgroundFill(Color.WHITE, null, null)); // Tıklanınca Beyaz

        // Butonun İlk Hali: Mor arka plan, Beyaz yazı
        nextLevelBtn.setBackground(purpBg);
        nextLevelBtn.setTextFill(Color.WHITE);

        // PDF Genel Kuralı: Tıklanma anında (Pressed) arka plan BEYAZ, yazı KIRMIZI olur
        nextLevelBtn.setOnMousePressed(e -> {
            nextLevelBtn.setBackground(clickBg);
            nextLevelBtn.setTextFill(Color.RED);
        });

        // Tıklama bırakıldığında (Released) bir sonraki seviyeye geçişi tetikler
        nextLevelBtn.setOnMouseReleased(e -> {
            nextLevelBtn.setBackground(purpBg);
            nextLevelBtn.setTextFill(Color.WHITE);
            
            // SceneManager'a bir sonraki seviyeyi başlatma emri verir
            sceneManager.startLevel(currentLevel + 1);
        });

        // Tüm elemanları panele ekliyoruz
        this.getChildren().addAll(mainLabel, subLabel, nextLevelBtn);
    }
}