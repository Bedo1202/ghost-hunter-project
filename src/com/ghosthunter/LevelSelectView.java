package com.ghosthunter;


/**
 * Marmara University - CSE 1242 Term Project
 * LevelSelectView Sınıfı - Harita Görselleri ve Arka Plan Problemini Çözen Versiyon
 */
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Marmara University - CSE 1242 Term Project [cite: 7, 9]
 * LevelSelectView Sınıfı - Kartların İçine Harita Görsellerini Tam Ortalar
 */
public class LevelSelectView extends VBox {

    private final SceneManager sceneManager;

    public LevelSelectView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // 1. DÜZEN VE HİZALAMA AYARLARI
        this.setAlignment(Pos.CENTER); 
        this.setSpacing(15); 
        this.setPadding(new Insets(20, 0, 20, 0));

        // 2. ANA ARKA PLAN GÖRSELİ (LevelSelect.png)
        try {
            Image bg = new Image(getClass().getResourceAsStream("LevelSelect.png"));
            BackgroundImage backgroundImage = new BackgroundImage(
                bg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1024, 768, false, false, false, true)
            );
            this.setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            this.setBackground(new Background(new BackgroundFill(Color.web("#150B24"), null, null)));
        }

        // 3. SEVİYE KARTLARININ OLUŞTURULMASI (Görseller ve Sayılar Üst Üste)
        Button lvl1 = createLevelCard("1", "Level1.png", 1);
        Button lvl2 = createLevelCard("2", "Level2.png", 2);
        Button lvl3 = createLevelCard("3", "Level3.png", 3);

        // 4. "BACK TO MENU" BUTONU
        Button backBtn = new Button("BACK TO MENU");
        backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backBtn.setPrefSize(180, 40);
        
        Background defaultBackBg = new Background(new BackgroundFill(Color.web("#800080"), null, null));
        Background clickBackBg = new Background(new BackgroundFill(Color.WHITE, null, null));
        
        backBtn.setBackground(defaultBackBg);
        backBtn.setTextFill(Color.WHITE);
        
        backBtn.setOnMousePressed(e -> {
            backBtn.setBackground(clickBackBg);
            backBtn.setTextFill(Color.RED); // [cite: 158]
        });
        backBtn.setOnMouseReleased(e -> {
            backBtn.setBackground(defaultBackBg);
            backBtn.setTextFill(Color.WHITE);
            sceneManager.showMainMenu();
        });

        this.getChildren().addAll(lvl1, lvl2, lvl3, backBtn);
    }

    /**
     * Döküman Sayfa 12 Kuralları: [cite: 166, 167, 168, 169]
     * Harita görseli ve sayı StackPane ile üst üste bindirilir.
     */
    private Button createLevelCard(String levelNumber, String imagePath, int levelNum) {
        // Butonun metnini boş bırakıyoruz çünkü StackPane ile içeriği kendimiz dolduracağız
        Button card = new Button();
        card.setPrefSize(160, 95); // Dökümandaki dikdörtgen kart boyutları 

        // Renk Tanımlamaları [cite: 167, 168, 169]
        Color normalPurple = Color.web("#800080");     
        Color lighterPurple = Color.web("#BA55D3");    
        Color clickedWhite = Color.WHITE;              

        // Katmanlı Düzen: Resim altta, yazı onun üstünde olacak
        StackPane cardContent = new StackPane();
        cardContent.setAlignment(Pos.CENTER);

        // 1. Katman: Küçük Harita Resmi 
        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(130);  // Mor kutunun içine tam oturacak genişlik
            imgView.setFitHeight(70);  // Mor kutunun içine tam oturacak yükseklik
            imgView.setPreserveRatio(false); // Tam sığdırma zorlaması
            cardContent.getChildren().add(imgView);
        } catch (Exception e) {
            System.out.println(imagePath + " harita resmi bulunamadı.");
        }

        // 2. Katman: Resmin Tam Üzerine Binecek Olan Beyaz Numara
        Label numLabel = new Label(levelNumber);
        numLabel.setFont(Font.font("Impact", FontWeight.BOLD, 32));
        numLabel.setTextFill(Color.WHITE);
        cardContent.getChildren().add(numLabel);

        // Oluşturduğumuz bu katmanlı yapıyı butonun içine grafik olarak gömüyoruz
        card.setGraphic(cardContent);

        // İlk Görünüm Ayarı [cite: 167]
        card.setBackground(new Background(new BackgroundFill(normalPurple, new CornerRadii(6), null)));

        // KURAL 1: Hover-over (Fare üzerine gelince) açık mor olur [cite: 168]
        card.setOnMouseEntered(e -> {
            if (card.getBackground().getFills().get(0).getFill() != clickedWhite) {
                card.setBackground(new Background(new BackgroundFill(lighterPurple, new CornerRadii(6), null)));
            }
        });

        card.setOnMouseExited(e -> {
            if (card.getBackground().getFills().get(0).getFill() != clickedWhite) {
                card.setBackground(new Background(new BackgroundFill(normalPurple, new CornerRadii(6), null)));
            }
        });

        // KURAL 2: Clicked State (Tıklanınca) arka plan beyaz olur [cite: 169]
        card.setOnMousePressed(e -> {
            card.setBackground(new Background(new BackgroundFill(clickedWhite, new CornerRadii(6), null)));
            numLabel.setTextFill(Color.web("#800080")); // Arka plan beyaz olunca yazı mor olsun ki okunsun
        });

        // KURAL 3: Released (Tıklama bırakılınca) seviye haritasını yükler [cite: 169]
        card.setOnMouseReleased(e -> {
            card.setBackground(new Background(new BackgroundFill(normalPurple, new CornerRadii(6), null)));
            numLabel.setTextFill(Color.WHITE);
            sceneManager.startLevel(levelNum); // Seviyeyi başlatır [cite: 169]
        });

        return card;
    }
}