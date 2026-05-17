package com.ghosthunter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

//Ali_Yüşa_Sezer_150124071
//Ahmet_Kayra_Demirci-150124056
public class PauseView extends StackPane {
    //oyunu durdurunca gelen ekran
    public PauseView(SceneManager sceneManager, GameController gameController, int currentLevel) {
      
        Rectangle bg = new Rectangle(1024, 768);
        bg.setFill(Color.rgb(0, 0, 0, 0.0));
        
        //butonların font ayarları
        Font buttonF = Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"), 30);
        if (buttonF == null) {
            buttonF = Font.font("Arial", 30);
        }
        Font buttongp = Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"), 50);
        if (buttongp == null) {
            buttongp = Font.font("Arial", 30);
        }
        
        //ekrana yazılan yazı
        Label title = new Label("GAME PAUSED");
        title.setFont(buttongp);
        title.setTextFill(Color.BLACK);
        
        // Butonların oluşturulması
        Button resumeB = new Button("RESUME");
        Button retryB = new Button("RETRY");
        Button menuB = new Button("MAIN MENU");

        
        resumeB.setFont(buttonF);
        retryB.setFont(buttonF);
        menuB.setFont(buttonF);

        resumeB.setPrefSize(200, 50);
        retryB.setPrefSize(200, 50);
        menuB.setPrefSize(200, 50);

        //Butonların renkleri
        Background defaultBg = new Background(new BackgroundFill(Color.PURPLE, null, null));
        Background clickBg = new Background(new BackgroundFill(Color.WHITE, null, null));
        resumeB.setBackground(defaultBg);
        resumeB.setTextFill(Color.WHITE);
        retryB.setBackground(defaultBg);
        retryB.setTextFill(Color.WHITE);
        menuB.setBackground(defaultBg);
        menuB.setTextFill(Color.WHITE);

        //Butonların anlık renkleri
        resumeB.setOnMousePressed(e -> { 
        	resumeB.setBackground(clickBg); resumeB.setTextFill(Color.RED); 
        	}
        );
        resumeB.setOnMouseReleased(e -> { 
        	resumeB.setBackground(defaultBg); resumeB.setTextFill(Color.WHITE); 
        	}
        );
        retryB.setOnMousePressed(e -> { 
        	retryB.setBackground(clickBg); retryB.setTextFill(Color.RED); 
        	}
        );
        retryB.setOnMouseReleased(e -> { 
        	retryB.setBackground(defaultBg); retryB.setTextFill(Color.WHITE); 
        	}
        );
        menuB.setOnMousePressed(e -> { 
        	menuB.setBackground(clickBg); menuB.setTextFill(Color.RED); 
        	}
        );
        menuB.setOnMouseReleased(e -> { 
        	menuB.setBackground(defaultBg); menuB.setTextFill(Color.WHITE); 
        	}
        );

        //Butonların aksiyonları
        resumeB.setOnAction(e -> {
            gameController.resumeGame();
            ((StackPane)getParent()).getChildren().remove(this);
        }
        );
        retryB.setOnAction(e -> {
            gameController.stopGame("Retry");
            sceneManager.startLevel(currentLevel);
        }
        );
        menuB.setOnAction(e -> {
            gameController.stopGame("Main Menu");
            sceneManager.showMainMenu();
        }
        );
        //Butonların dizaynı
        VBox layout = new VBox(20, title, resumeB, retryB, menuB);
        layout.setAlignment(Pos.CENTER);

        getChildren().addAll(bg, layout);
    }
}
