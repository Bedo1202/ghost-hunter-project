package com.ghosthunter;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
/**
 * Grup Projesi - SceneManager Sınıfı (Mevcut dosyanın içerisine)
 * Amaç: Harita görsel temalarını yüklemek ve panelleri (Win/Lose) yönetmek[cite: 23, 179].
 */
public class SceneManager {

    private final Stage primaryStage;
    private final int windowWidth = 1024;
    private final int windowHeight = 768;
    

    private GameController gameController;

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
    	StackPane outerRoot = new StackPane();
    	outerRoot.setStyle("-fx-background-color: black;");

    	StackPane gameRootContainer = new StackPane();
    	gameRootContainer.setPrefSize(windowWidth, windowHeight);
    	gameRootContainer.setMinSize(windowWidth, windowHeight);
    	gameRootContainer.setMaxSize(windowWidth, windowHeight);

    	Pane mapPlayAreaPane = new Pane();
    	mapPlayAreaPane.setPrefSize(windowWidth, windowHeight);
    	mapPlayAreaPane.setMinSize(windowWidth, windowHeight);
    	mapPlayAreaPane.setMaxSize(windowWidth, windowHeight);

        // PDF Sayfa 5, 6: Seviyelerin Kendine Has Harita Görsellerinin Atanması
        try {
        	// 1. Resim ismini senin attığın dosya adına (Level1.png vb.) eşitledik
        	String imageFileName = "Level" + levelNumber + ".png"; 

        	// 2. Resmin yüklenirken boyutlarının pencereye tam oturmasını sağladık
        	Image mapImage = new Image(getClass().getResourceAsStream(imageFileName), windowWidth, windowHeight, false, true);
        	ImageView mapView = new ImageView(mapImage);
        	mapView.setFitWidth(windowWidth);
        	mapView.setFitHeight(windowHeight);
        	mapView.setPreserveRatio(false);

        	mapPlayAreaPane.getChildren().add(mapView);
        } catch (Exception e) {
            // PDF Tematik Alternatif Renkleri (Görseller yüklenemezse devreye girer)
            if (levelNumber == 1) mapPlayAreaPane.setStyle("-fx-background-color: #2E1A47;"); // Level 1: Deep Purple 
            else if (levelNumber == 2) mapPlayAreaPane.setStyle("-fx-background-color: #1A3636;"); // Level 2: Cold Teal 
            else mapPlayAreaPane.setStyle("-fx-background-color: #3D0C02;"); // Level 3: Burnt Red 
        }

        // HUD Katmanının Eklenmesi
        GameHUD gameHUD = new GameHUD();
        gameRootContainer.getChildren().addAll(mapPlayAreaPane, gameHUD.getContainer());
        outerRoot.getChildren().add(gameRootContainer);

        Scene gameScene = new Scene(outerRoot, windowWidth, windowHeight);
        primaryStage.setScene(gameScene);

        DoubleBinding scaleBinding = (DoubleBinding) Bindings.min(
                gameScene.widthProperty().divide(windowWidth),
                gameScene.heightProperty().divide(windowHeight)
        );

        gameRootContainer.scaleXProperty().bind(scaleBinding);
        gameRootContainer.scaleYProperty().bind(scaleBinding);

        // KLAVYE KONTROLLERİ (W,A,S,D ve SPACE)
        gameController = new GameController(this, mapPlayAreaPane, gameHUD, gameRootContainer);
        gameController.startLevel(levelNumber);
        
        gameScene.setOnKeyPressed(e -> {
            gameController.handleInput(e.getCode(), true);
        });
        gameScene.setOnKeyReleased(e -> {
            gameController.handleInput(e.getCode(), false);
        });
        gameScene.setOnMouseMoved(e -> {
            Point2D localPoint = mapPlayAreaPane.sceneToLocal(e.getSceneX(), e.getSceneY());
            gameController.handleMouseMoved(localPoint.getX(), localPoint.getY());
        });

        gameScene.setOnMouseDragged(e -> {
            Point2D localPoint = mapPlayAreaPane.sceneToLocal(e.getSceneX(), e.getSceneY());
            gameController.handleMouseMoved(localPoint.getX(), localPoint.getY());
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