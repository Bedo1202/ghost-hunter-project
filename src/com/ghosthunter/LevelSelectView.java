package com.ghosthunter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Grup Projesi - LevelSelectView Sınıfı
 * Amaç: PDF Sayfa 12'deki orijinal görsel üzerine tam oturan şeffaf/renkli dikey seçim alanları.
 */
public class LevelSelectView extends VBox {

    private final SceneManager sceneManager;

    public LevelSelectView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // 1. PANEL YERLEŞİM AYARLARI
        this.setAlignment(Pos.CENTER); // Elemanları merkeze hizala
        this.setSpacing(18); // Resimdeki mor kutuların arasındaki dikey boşluğa göre ayarlandı
        this.setPadding(new Insets(120, 0, 0, 0)); // Kutuları dikeyde resmin üzerine tam oturtmak için üst boşluk

        // 2. ARKA PLAN GÖRSELİ (level_select_bg.png)
        try {
        	Image bg = new Image(getClass().getResourceAsStream("LevelSelect.png"));
            this.setBackground(new Background(new BackgroundImage(bg, 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, new BackgroundSize(1024, 768, false, false, false, false))));
        } catch (Exception e) {
            this.setBackground(new Background(new BackgroundFill(Color.web("#150B24"), null, null)));
        }

        // 3. RESİMDEKİ KUTULARIN ÜZERİNE GELECEK ETKİLEŞİMLİ ALANLAR (BUTONLAR)
        // Yazı eklemiyoruz (""), çünkü resimde zaten Level 1, Level 2 yazıyor!
        Button lvl1 = createTransparentLevelButton(1);
        Button lvl2 = createTransparentLevelButton(2);
        Button lvl3 = createTransparentLevelButton(3);

        // 4. ANA MENÜYE DÖNÜŞ BUTONU (Resmin en altında boş bir yere konumlanır)
        Button backBtn = new Button("BACK TO MENU");
        backBtn.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-weight: bold;");
        backBtn.setPrefSize(140, 30);
        backBtn.setOnAction(e -> sceneManager.showMainMenu());

        // Hepsini sırayla ekrana diziyoruz
        this.getChildren().addAll(lvl1, lvl2, lvl3, backBtn);
    }

    /**
     * PDF İsterleri:
     * 1. Hover-over: Lighter purple (Hafif saydam açık mor)
     * 2. Clicked state: White background, Red border/effect
     */
    private Button createTransparentLevelButton(int levelNum) {
        Button btn = new Button(""); // İçi boş, resmi kapatmasın diye
        
        // Resimdeki mor kutuların tam boyutları (Genişlik: 420, Yükseklik: 110 civarı)
        btn.setPrefSize(420, 110); 

        // Renk tanımlamaları (Resim alttan görünsün diye yarı saydam renkler kullanıyoruz)
        Background transparentBg = new Background(new BackgroundFill(Color.TRANSPARENT, null, null));
        
        // Hover: PDF İsteri olan "Lighter Purple" (%40 opaklık verilerek arkadaki yazıların görünmesi sağlandı)
        Background hoverBg = new Background(new BackgroundFill(Color.web("#BA55D3", 0.4), null, null)); 
        
        // Clicked: PDF İsteri olan "White" (%60 opaklık ile beyaz bir parlama efekti verir)
        Background clickBg = new Background(new BackgroundFill(Color.web("#FFFFFF", 0.6), null, null));

        // İlk başta buton tamamen görünmezdir, alttaki harika görsel görünür
        btn.setBackground(transparentBg);
        btn.setBorder(Border.EMPTY);

        // KURAL 1: Fare üzerine geldiğinde (Hover) kutu tatlı bir açık mora bürünür
        btn.setOnMouseEntered(e -> btn.setBackground(hoverBg));

        // Fare çekildiğinde tekrar görünmez olur
        btn.setOnMouseExited(e -> btn.setBackground(transparentBg));

        // KURAL 2: Tıklandığı an (Pressed) kutunun içi beyaz parlar
        btn.setOnMousePressed(e -> btn.setBackground(clickBg));

        // KURAL 3: Tıklama bırakıldığı an (Released) seviyeyi başlatır
        btn.setOnMouseReleased(e -> {
            btn.setBackground(transparentBg);
            sceneManager.startLevel(levelNum);
        });

        return btn;
    }
}