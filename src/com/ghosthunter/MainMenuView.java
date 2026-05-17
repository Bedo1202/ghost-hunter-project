package com.ghosthunter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
public class MainMenuView extends VBox {

    private final SceneManager sceneManager;

    public MainMenuView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        //Butonların konum ayarları
        this.setAlignment(Pos.CENTER_LEFT); 
        this.setSpacing(15); 
        this.setPadding(new Insets(100, 0, 0, 150)); 

        //Arka plan
        try {
            Image backG = new Image(getClass().getResourceAsStream("MainMenu.png"));
            
            BackgroundSize bSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
            BackgroundImage backgroundImage = new BackgroundImage(backG,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, bSize);
                
            this.setBackground(new Background(backgroundImage));
        } 
        catch (Exception e) {
            // Eğer resim bulunamazsa veya yüklenirken bir hata oluşursa ekran mor renge boyanır
            this.setBackground(new Background(new BackgroundFill(Color.PURPLE, null, null)));
        }
        // Butonların oluşturulması
        Button startB = createSB("START GAME");
        Button levelB = createSB("SELECT LEVEL");
        Button exitB = createSB("EXIT");

        // Butonların yapacakları
        startB.setOnAction(e -> sceneManager.startLevel(1));
        levelB.setOnAction(e -> sceneManager.showLevelSelection());
        exitB.setOnAction(e -> System.exit(0));
        //Kutulara ekler
        this.getChildren().addAll(startB, levelB, exitB);
    }

   
    private Button createSB(String text) {
        //Buton yazısı ayarları
    	Button btn = new Button(text);
        Font buttonF = Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),30);
        if (buttonF == null) {
            buttonF = Font.font("Arial", 30);
        }
        //Buton ayarları
        btn.setFont(buttonF);
        btn.setPrefSize(200, 50);
        Background defaultBg = new Background(new BackgroundFill(Color.PURPLE, null, null));
        Background clickBg = new Background(new BackgroundFill(Color.WHITE, null, null));
        //Butonların anlık durumları
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