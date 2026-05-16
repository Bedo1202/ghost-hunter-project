package com.ghosthunter;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Grup Projesi - SceneManager Sınıfı (Mevcut dosyanın içerisine)
 * Amaç: Harita görsel temalarını yüklemek ve panelleri (Win/Lose) yönetmek[cite: 23, 179].
 */
public class SceneManager {

    private final Stage primaryStage;
    private final int windowWidth = 1024;
    private final int windowHeight = 768;

    // TODO: Engine dalı bitince burası bağlanacak
    // private GameController gameController;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showMainMenu() {
        MainMenuView menuView = new MainMenuView(this);
        primaryStage.setScene(new Scene(menuView, windowWidth, windowHeight));
        primaryStage.setTitle("Ghost Hunter Inc.");
        primaryStage.show();
    }

    public void showLevelSelection() {
        LevelSelectView levelView = new LevelSelectView(this);
        primaryStage.setScene(new Scene(levelView, windowWidth, windowHeight));
    }

    public void startLevel(int levelNumber) {
        StackPane gameRootContainer = new StackPane();
        Pane mapPlayAreaPane = new Pane();

        // PDF Sayfa 5, 6: Seviyelerin Kendine Has Harita Görsellerinin Atanması
        try {
        	// 1. Resim ismini senin attığın dosya adına (Level1.png vb.) eşitledik
        	String imageFileName = "Level" + levelNumber + ".png"; 

        	// 2. Resmin yüklenirken boyutlarının pencereye tam oturmasını sağladık
        	Image mapImage = new Image(getClass().getResourceAsStream(imageFileName), windowWidth, windowHeight, false, true);
            mapPlayAreaPane.setBackground(new Background(new BackgroundImage(mapImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(windowWidth, windowHeight, false, false, false, false))));
        } catch (Exception e) {
            // PDF Tematik Alternatif Renkleri (Görseller yüklenemezse devreye girer)
            if (levelNumber == 1) mapPlayAreaPane.setStyle("-fx-background-color: #2E1A47;"); // Level 1: Deep Purple 
            else if (levelNumber == 2) mapPlayAreaPane.setStyle("-fx-background-color: #1A3636;"); // Level 2: Cold Teal 
            else mapPlayAreaPane.setStyle("-fx-background-color: #3D0C02;"); // Level 3: Burnt Red 
        }

        // HUD Katmanının Eklenmesi
        GameHUD gameHUD = new GameHUD();
        gameRootContainer.getChildren().addAll(mapPlayAreaPane, gameHUD.getContainer());

        Scene gameScene = new Scene(gameRootContainer, windowWidth, windowHeight);
        primaryStage.setScene(gameScene);

        // KLAVYE KONTROLLERİ (W,A,S,D ve SPACE)
        gameScene.setOnKeyPressed(e -> {
            // TODO: gameController.handleInput(e.getCode(), true);
        });
        gameScene.setOnKeyReleased(e -> {
            // TODO: gameController.handleInput(e.getCode(), false);
        });

        // SIMÜLASYON TETİKLEME ALANI (Motorsuz test aşaması için)
        gameScene.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                triggerWinOverlay(gameRootContainer, levelNumber);
            } else if (event.isAltDown()) {
                triggerLoseOverlay(gameRootContainer, levelNumber, 240);
            }
        });
    }

    public void triggerWinOverlay(StackPane gameRoot, int currentLevel) {
        WinScreen winScreen = new WinScreen(this, currentLevel);
        gameRoot.getChildren().add(winScreen);
    }

    public void triggerLoseOverlay(StackPane gameRoot, int currentLevel, int score) {
        LoseScreen loseScreen = new LoseScreen(this, currentLevel, score);
        gameRoot.getChildren().add(loseScreen);
    }
}