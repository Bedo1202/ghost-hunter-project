package com.ghosthunter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//Ali_Yüşa_Sezer_150124071
public class WinScreen extends VBox {

    public WinScreen(SceneManager sceneManager, int currentLevel) {
        // Panel ayarları
        this.setAlignment(Pos.CENTER); 
        this.setSpacing(15); 

        // 2. YARI SAYDAM SİYAH ARKA PLAN (PDF İsteri)
        // Color.rgb(0, 0, 0, 0.75) -> Sondaki 0.75 değeri %75 opaklık sağlar. 
        // Böylece arkadaki oyun haritası hafif kararmış şekilde alttan görünür.
        this.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.75), null, null)));

        // 3. "YOU WIN!" BAŞLIĞI
        Label mainLabel = new Label(currentLevel >= 3 ? "GAME COMPLETED!" : "YOU WIN!");
        mainLabel.setFont(Font.font("Impact", FontWeight.BOLD, 44)); // Görseldeki kalın font stili
        mainLabel.setTextFill(Color.WHITE);

        // 4. ALTTARAFINDAKİ BİLGİLENDİRME METNİ
        Label subLabel = new Label(currentLevel >= 3
                ? "YOU HAVE VACUUMED ALL LEVELS!"
                : "YOU'VE VACUUMED ALL THE GHOSTS!");
        subLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        subLabel.setTextFill(Color.WHITE);

        // 5. "NEXT LEVEL" BUTONU VE TIKLAMA RENK DEĞİŞİMİ
        boolean isFinalLevel = currentLevel >= 3;
        Button nextLevelBtn = new Button(isFinalLevel ? "MAIN MENU" : "NEXT LEVEL");
        nextLevelBtn.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),14));
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
            if (isFinalLevel) {
                sceneManager.showMainMenu();
            } else {
                sceneManager.startLevel(currentLevel + 1);
            }
        });

        // Tüm elemanları panele ekliyoruz
        this.getChildren().addAll(mainLabel, subLabel, nextLevelBtn);
    }
}