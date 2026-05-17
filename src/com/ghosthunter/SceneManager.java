package com.ghosthunter;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

//Ali_Yüşa_Sezer_150124071

public class SceneManager {

    private final Stage primaryS;
    private final Scene mainS;       // Oyun boyunca TEK bir sahne olacak
    private final StackPane mainR;     // Ekranların değişeceği ana taşıyıcı panel
    
    private final int windowW = 1024;
    private final int windowH = 768;

    private GameController gameController;

    public SceneManager(Stage primaryStage) {
        this.primaryS = primaryStage;
        
        // 1. ADIM: Ana kök paneli ve tek olan sahnemizi oluşturuyoruz
        this.mainR = new StackPane();
        this.mainS = new Scene(mainR, windowW, windowH);
        
        // Sahneyi stage'e SADECE BİR KERE bağlıyoruz
        this.primaryS.setScene(mainS);
        this.primaryS.setFullScreenExitHint("");
    }

    public void showMainMenu() {
        MainMenuView menuView = new MainMenuView(this);
        
        // AKILLILIK: Sahneyi değiştirmiyoruz, sadece içindeki paneli değiştiriyoruz!
        mainR.getChildren().setAll(menuView); 
        
        primaryS.setTitle("Ghost Hunter Inc.");
        primaryS.show();
    }

    public void showLevelSelection() {
        LevelSelectView levelView = new LevelSelectView(this);
        
        // Eski ekranı temizler, seviye seçim ekranını içeriğe basar
        mainR.getChildren().setAll(levelView); 
        primaryS.show();
    }

    public void startLevel(int levelNumber) {
        StackPane gameRootContainer = new StackPane();
        gameRootContainer.setPrefSize(windowW, windowH);
        gameRootContainer.setMinSize(windowW, windowH);
        gameRootContainer.setMaxSize(windowW, windowH);

        Pane mapPlayAreaPane = new Pane();
        ImageView fullBackground = new ImageView();
        fullBackground.setPreserveRatio(false);
        fullBackground.fitWidthProperty().bind(mainS.widthProperty());
        fullBackground.fitHeightProperty().bind(mainS.heightProperty());
        mapPlayAreaPane.setPrefSize(windowW, windowH);
        mapPlayAreaPane.setMinSize(windowW, windowH);
        mapPlayAreaPane.setMaxSize(windowW, windowH);

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

        gameHUD.getContainer().prefWidthProperty().bind(mainS.widthProperty());
        gameHUD.getContainer().prefHeightProperty().bind(mainS.heightProperty());

        // Otomatik Ölçekleme (Resize) bağlarını tek olan ana sahnemize (mainScene) bağlıyoruz
        DoubleBinding scaleBinding = (DoubleBinding) Bindings.min(
                mainS.widthProperty().divide(windowW),
                mainS.heightProperty().divide(windowH)
        );
        gameRootContainer.scaleXProperty().bind(scaleBinding);
        gameRootContainer.scaleYProperty().bind(scaleBinding);

        // KLAVYE VE FARE AYARLARINI ANA SAHNEYE BAĞLIYORUZ
        gameController = new GameController(this, mapPlayAreaPane, gameHUD, gameRootContainer);
        gameController.startLevel(levelNumber);
        
        mainS.setOnKeyPressed(e -> gameController.handleInput(e.getCode(), true));
        mainS.setOnKeyReleased(e -> gameController.handleInput(e.getCode(), false));
        mainS.setOnMouseMoved(e -> {
            Point2D localPoint = mapPlayAreaPane.sceneToLocal(e.getSceneX(), e.getSceneY());
            gameController.handleMouseMoved(localPoint.getX(), localPoint.getY());
        });
        mainS.setOnMouseDragged(e -> {
            Point2D localPoint = mapPlayAreaPane.sceneToLocal(e.getSceneX(), e.getSceneY());
            gameController.handleMouseMoved(localPoint.getX(), localPoint.getY());
        });

        // Ana kökü temizleyip oyun panelini basıyoruz
        mainR.getChildren().setAll(fullBackground, gameRootContainer, gameHUD.getContainer());
        primaryS.show();
    }

    public void triggerWinOverlay(StackPane gameRoot, int currentLevel) {
        WinScreen winScreen = new WinScreen(this, currentLevel);

        winScreen.prefWidthProperty().bind(mainS.widthProperty());
        winScreen.prefHeightProperty().bind(mainS.heightProperty());

        mainR.getChildren().add(winScreen);
    }

    public void triggerLoseOverlay(StackPane gameRoot, int currentLevel, int score) {
    	LoseScreen loseS = new LoseScreen(this, currentLevel, score);

        loseS.prefWidthProperty().bind(mainS.widthProperty());
        loseS.prefHeightProperty().bind(mainS.heightProperty());

        mainR.getChildren().add(loseS);
    }
    public StackPane getMainRoot() {
        return mainR;
    }
}