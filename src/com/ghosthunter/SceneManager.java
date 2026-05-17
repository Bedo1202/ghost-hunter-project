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
 * Grup Projesi - SceneManager Sınıfı
 * Amaç: Tek Sahne (Single Scene) mimarisi ile tam ekrandan atma sorununu kökten çözmek.
 */
public class SceneManager {

    private final Stage primaryStage;
    private final Scene mainScene;       // Oyun boyunca TEK bir sahne olacak
    private final StackPane mainRoot;     // Ekranların değişeceği ana taşıyıcı panel
    
    private final int windowWidth = 1024;
    private final int windowHeight = 768;

    private GameController gameController;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // 1. ADIM: Ana kök paneli ve tek olan sahnemizi oluşturuyoruz
        this.mainRoot = new StackPane();
        this.mainScene = new Scene(mainRoot, windowWidth, windowHeight);
        
        // Sahneyi stage'e SADECE BİR KERE bağlıyoruz
        this.primaryStage.setScene(mainScene);
        this.primaryStage.setFullScreenExitHint("");
    }

    public void showMainMenu() {
        MainMenuView menuView = new MainMenuView(this);
        
        // AKILLILIK: Sahneyi değiştirmiyoruz, sadece içindeki paneli değiştiriyoruz!
        mainRoot.getChildren().setAll(menuView); 
        
        primaryStage.setTitle("Ghost Hunter Inc.");
        primaryStage.show();
    }

    public void showLevelSelection() {
        LevelSelectView levelView = new LevelSelectView(this);
        
        // Eski ekranı temizler, seviye seçim ekranını içeriğe basar
        mainRoot.getChildren().setAll(levelView); 
        primaryStage.show();
    }

    public void startLevel(int levelNumber) {
        StackPane gameRootContainer = new StackPane();
        gameRootContainer.setPrefSize(windowWidth, windowHeight);
        gameRootContainer.setMinSize(windowWidth, windowHeight);
        gameRootContainer.setMaxSize(windowWidth, windowHeight);

        Pane mapPlayAreaPane = new Pane();
        ImageView fullBackground = new ImageView();
        fullBackground.setPreserveRatio(false);
        fullBackground.fitWidthProperty().bind(mainScene.widthProperty());
        fullBackground.fitHeightProperty().bind(mainScene.heightProperty());
        mapPlayAreaPane.setPrefSize(windowWidth, windowHeight);
        mapPlayAreaPane.setMinSize(windowWidth, windowHeight);
        mapPlayAreaPane.setMaxSize(windowWidth, windowHeight);

        try {
            String imageFileName = "Level" + levelNumber + ".png"; 
            Image mapImage = new Image(getClass().getResourceAsStream(imageFileName));
            fullBackground.setImage(mapImage);
        } catch (Exception e) {
            if (levelNumber == 1) mapPlayAreaPane.setStyle("-fx-background-color: #2E1A47;"); 
            else if (levelNumber == 2) mapPlayAreaPane.setStyle("-fx-background-color: #1A3636;"); 
            else mapPlayAreaPane.setStyle("-fx-background-color: #3D0C02;"); 
        }

        GameHUD gameHUD = new GameHUD();
        gameRootContainer.getChildren().add(mapPlayAreaPane);

        gameHUD.getContainer().prefWidthProperty().bind(mainScene.widthProperty());
        gameHUD.getContainer().prefHeightProperty().bind(mainScene.heightProperty());

        // Otomatik Ölçekleme (Resize) bağlarını tek olan ana sahnemize (mainScene) bağlıyoruz
        DoubleBinding scaleBinding = (DoubleBinding) Bindings.min(
                mainScene.widthProperty().divide(windowWidth),
                mainScene.heightProperty().divide(windowHeight)
        );
        gameRootContainer.scaleXProperty().bind(scaleBinding);
        gameRootContainer.scaleYProperty().bind(scaleBinding);

        // KLAVYE VE FARE AYARLARINI ANA SAHNEYE BAĞLIYORUZ
        gameController = new GameController(this, mapPlayAreaPane, gameHUD, gameRootContainer);
        gameController.startLevel(levelNumber);
        
        mainScene.setOnKeyPressed(e -> gameController.handleInput(e.getCode(), true));
        mainScene.setOnKeyReleased(e -> gameController.handleInput(e.getCode(), false));
        mainScene.setOnMouseMoved(e -> {
            Point2D localPoint = mapPlayAreaPane.sceneToLocal(e.getSceneX(), e.getSceneY());
            gameController.handleMouseMoved(localPoint.getX(), localPoint.getY());
        });
        mainScene.setOnMouseDragged(e -> {
            Point2D localPoint = mapPlayAreaPane.sceneToLocal(e.getSceneX(), e.getSceneY());
            gameController.handleMouseMoved(localPoint.getX(), localPoint.getY());
        });

        // Ana kökü temizleyip oyun panelini basıyoruz
        mainRoot.getChildren().setAll(fullBackground, gameRootContainer, gameHUD.getContainer());
        primaryStage.show();
    }

    public void triggerWinOverlay(StackPane gameRoot, int currentLevel) {
        WinScreen winScreen = new WinScreen(this, currentLevel);

        winScreen.prefWidthProperty().bind(mainScene.widthProperty());
        winScreen.prefHeightProperty().bind(mainScene.heightProperty());

        mainRoot.getChildren().add(winScreen);
    }

    public void triggerLoseOverlay(StackPane gameRoot, int currentLevel, int score) {
        LoseScreen loseScreen = new LoseScreen(this, currentLevel, score);

        loseScreen.prefWidthProperty().bind(mainScene.widthProperty());
        loseScreen.prefHeightProperty().bind(mainScene.heightProperty());

        mainRoot.getChildren().add(loseScreen);
    }
    public StackPane getMainRoot() {
        return mainRoot;
    }
}