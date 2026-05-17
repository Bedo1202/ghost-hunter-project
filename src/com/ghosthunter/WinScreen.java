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

        //Arka plan ayarları
        this.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.75), null, null)));

        // Kazandın yazısı
        Label mainL = new Label(currentLevel >= 3 ? "GAME COMPLETED!" : "YOU WIN!");
        mainL.setFont(Font.font("Impact", FontWeight.BOLD, 45)); 
        mainL.setTextFill(Color.WHITE);

        //altındaki bilgilendirme kısmı 
        Label subLabel = new Label(currentLevel >= 3
                ? "YOU HAVE VACUUMED ALL LEVELS!"
                : "YOU'VE VACUUMED ALL THE GHOSTS!");
        subLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        subLabel.setTextFill(Color.WHITE);

        // Sonraki seviyeye geçiş
        boolean isFinalLevel = currentLevel >= 3;
        Button nextLevelBtn = new Button(isFinalLevel ? "MAIN MENU" : "NEXT LEVEL");
        nextLevelBtn.setFont(Font.loadFont(getClass().getResourceAsStream("Creepster-Regular.ttf"),14));
        nextLevelBtn.setPrefSize(200, 50); // 

        //renkler
        Background purpBg = new Background(new BackgroundFill(Color.PURPLE, null, null)); 
        Background clickBg = new Background(new BackgroundFill(Color.WHITE, null, null)); 

        //Butonun anlık renkleri
        nextLevelBtn.setBackground(purpBg);
        nextLevelBtn.setTextFill(Color.WHITE);

        nextLevelBtn.setOnMousePressed(e -> {
            nextLevelBtn.setBackground(clickBg);
            nextLevelBtn.setTextFill(Color.RED);
        });

        nextLevelBtn.setOnMouseReleased(e -> {
            nextLevelBtn.setBackground(purpBg);
            nextLevelBtn.setTextFill(Color.WHITE);
            
            //seviyeye göre sonraki ekranı verir
            if (isFinalLevel) {
                sceneManager.showMainMenu();
            } else {
                sceneManager.startLevel(currentLevel + 1);
            }
        });

        // Tüm elemanları panele ekliyoruz
        this.getChildren().addAll(mainL, subLabel, nextLevelBtn);
    }
}