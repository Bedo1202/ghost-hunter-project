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
        
        //Butonların konum ayarları
        this.setAlignment(Pos.CENTER_LEFT); 
        this.setSpacing(15); 
        this.setPadding(new Insets(100, 0, 0, 150)); 

        //Arka plan
        try {
            Image bg = new Image(getClass().getResourceAsStream("Mainmenu.png"));
            BackgroundImage bgı = new BackgroundImage(bg,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,new BackgroundSize(100, 100, true, true, false, true));
            this.setBackground(new Background(bgı));
        } catch (Exception e) {
            
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