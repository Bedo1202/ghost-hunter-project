package com.ghosthunter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


//Ali_Yüşa_Sezer_150124071

public class LevelSelectView extends VBox {

	//Ekran oluşturur
    private final SceneManager sceneManager;

    public LevelSelectView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        //Panel ayarları
        this.setAlignment(Pos.CENTER); 
        this.setSpacing(15); 
        this.setPadding(new Insets(20, 0, 20, 0));

        //Arka plan görselini ayarlar
        try {
            Image backG = new Image(getClass().getResourceAsStream("LevelSelect.png"));
            
            BackgroundSize bSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
            
            BackgroundImage backgroundImage = new BackgroundImage(backG, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, bSize);
                this.setBackground(new Background(backgroundImage));
        } 
        catch (Exception e) {
            this.setBackground(new Background(new BackgroundFill(Color.PURPLE, null, null)));
        }
        //Butonları yaratır
        Button l1 = createLC("1", "Level1.png", 1);
        Button l2 = createLC("2", "Level2.png", 2);
        Button l3 = createLC("3", "Level3.png", 3);

        //ana menüye dönüş butonu
        Button backB = new Button("BACK TO MENU");
        backB.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"), 30));
        backB.setPrefSize(250, 60);
      
        //Buton renkleri
        Background defaultBackBg = new Background(new BackgroundFill(Color.web("#800080"), null, null));
        Background clickBackBg = new Background(new BackgroundFill(Color.WHITE, null, null));
        
        backB.setBackground(defaultBackBg);
        backB.setTextFill(Color.WHITE);
        
        backB.setOnMousePressed(e -> {
            backB.setBackground(clickBackBg);
            backB.setTextFill(Color.RED); 
        }
        );
        backB.setOnMouseReleased(e -> {
            backB.setBackground(defaultBackBg);
            backB.setTextFill(Color.WHITE);
            sceneManager.showMainMenu();
        }
        );
        
        this.getChildren().addAll(l1, l2, l3, backB);
    }

    //Butonların oluşturulduğu yer
    private Button createLC(String levelNumber, String imagePath, int levelNum) {
        //Buton yaratılması
        Button card = new Button();
        card.setPrefSize(250, 150); 

        
        Color normalPurple = Color.PURPLE;     
        Color lighterPurple = Color.MEDIUMPURPLE;    
        Color clickedWhite = Color.WHITE;              

        //Elamanları sıralar
        StackPane cardC = new StackPane();
        cardC.setAlignment(Pos.CENTER);

        //Butonlara resimlerini ekler 
        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imgView = new ImageView(img);
            //Resmi boyutlandırır
            imgView.setFitWidth(200);  
            imgView.setFitHeight(120);  
            imgView.setPreserveRatio(false); 
            cardC.getChildren().add(imgView);
        } catch (Exception e) {
        	System.err.println("WARNİNG: " + imagePath + " is not found!");
        }

        //Butonlara numaraları yazar
        Label numLabel = new Label(levelNumber);
        numLabel.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"), 45));
        numLabel.setTextFill(Color.WHITE);
        cardC.getChildren().add(numLabel);

        //Butonları düzenler
        card.setGraphic(cardC);
        card.setBackground(new Background(new BackgroundFill(normalPurple, new CornerRadii(6), null)));

        // Fare üstüne gelirse renk değişimi
        card.setOnMouseEntered(e -> {
            if (card.getBackground().getFills().get(0).getFill() != clickedWhite) {
                card.setBackground(new Background(new BackgroundFill(lighterPurple, new CornerRadii(6), null)));
            }
        }
        );

        card.setOnMouseExited(e -> {
            if (card.getBackground().getFills().get(0).getFill() != clickedWhite) {
                card.setBackground(new Background(new BackgroundFill(normalPurple, new CornerRadii(6), null)));
            }
        }
        );

        // Fare tıklarsa renk değişimi
        card.setOnMousePressed(e -> {
            card.setBackground(new Background(new BackgroundFill(clickedWhite, new CornerRadii(6), null)));
            numLabel.setTextFill(Color.PURPLE); // 
        }
        );

        // Fare tıklaması gidince haritayı yükler
        card.setOnMouseReleased(e -> {
            card.setBackground(new Background(new BackgroundFill(normalPurple, new CornerRadii(6), null)));
            numLabel.setTextFill(Color.WHITE);
            sceneManager.startLevel(levelNum);
        }
        );

        return card;
    }
}