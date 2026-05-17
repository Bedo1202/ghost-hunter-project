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
	//Pencere, sahne ve taşıyıcı
    private final Stage primaryS;
    private final Scene mainS;       
    private final StackPane mainR;     
    //Oyun çözünürlüğü
    private final int windowW = 1024;
    private final int windowH = 768;
    
    private GameController gameController;
    //Oyunun ana yeri 
    public SceneManager(Stage primaryStage) {
        this.primaryS = primaryStage;
        
        //Sahneleri oluşturuyoruz
        this.mainR = new StackPane();
        this.mainS = new Scene(mainR, windowW, windowH);
        
        // Sahneyi pencereye 1 kez bağlıyoruz
        this.primaryS.setScene(mainS);
        this.primaryS.setFullScreenExitHint("");
    }

    public void showMainMenu() {
    	//Arayüz oluşturuyoruz
        MainMenuView menuV = new MainMenuView(this);
        
        //Arayüzü koyuyoruz
        mainR.getChildren().setAll(menuV); 
        
        primaryS.setTitle("Ghost Hunter Inc.");
        primaryS.show();
    }

    public void showLevelSelection() {
        LevelSelectView levelV = new LevelSelectView(this);
        
        // Eski arayüzü temizleyip yenisibi koyuyoruz
        mainR.getChildren().setAll(levelV); 
        primaryS.show();
    }
    //Seçilen seviyeyi açar
    public void startLevel(int levelNumber) {
        StackPane gameRC = new StackPane();
        //nesneleri tutan konteynerler
        gameRC.setPrefSize(windowW, windowH);
        gameRC.setMinSize(windowW, windowH);
        gameRC.setMaxSize(windowW, windowH);
        //karakterlerin gezineceği kordinat sistemi
        Pane mapPlayAP = new Pane();
        //resim ayarları
        ImageView fullBackground = new ImageView();
        fullBackground.setPreserveRatio(false);
        fullBackground.fitWidthProperty().bind(mainS.widthProperty());
        fullBackground.fitHeightProperty().bind(mainS.heightProperty());
        mapPlayAP.setPrefSize(windowW, windowH);
        mapPlayAP.setMinSize(windowW, windowH);
        mapPlayAP.setMaxSize(windowW, windowH);
        //Harita görselinin seçilmesi
        try {
            String imageFN = "Level" + levelNumber + ".png"; 
            Image mapImage = new Image(getClass().getResourceAsStream(imageFN));
            fullBackground.setImage(mapImage);
        } catch (Exception e) {
            if (levelNumber == 1) mapPlayAP.setStyle("-fx-background-color: #2E1A47;"); 
            else if (levelNumber == 2) mapPlayAP.setStyle("-fx-background-color: #1A3636;"); 
            else mapPlayAP.setStyle("-fx-background-color: #3D0C02;"); 
        }

        //Göstergeleri yani HUD'u çağırır
        GameHUD gameHUD = new GameHUD();
        gameRC.getChildren().add(mapPlayAP);

        gameHUD.getContainer().prefWidthProperty().bind(mainS.widthProperty());
        gameHUD.getContainer().prefHeightProperty().bind(mainS.heightProperty());

        // Otomatik ölçeklendirme
        DoubleBinding scaleBinding = (DoubleBinding) Bindings.min(mainS.widthProperty().divide(windowW),mainS.heightProperty().divide(windowH));
        //Oyun alanını orantılar
        gameRC.scaleXProperty().bind(scaleBinding);
        gameRC.scaleYProperty().bind(scaleBinding);

        // Klavye ve fareyi ana ekrana bağlıyoruz
        gameController = new GameController(this, mapPlayAP, gameHUD, gameRC);
        gameController.startLevel(levelNumber);
        
        mainS.setOnKeyPressed(e -> gameController.handleInput(e.getCode(), true));
        mainS.setOnKeyReleased(e -> gameController.handleInput(e.getCode(), false));
        mainS.setOnMouseMoved(e -> {
            Point2D localPoint = mapPlayAP.sceneToLocal(e.getSceneX(), e.getSceneY());
            gameController.handleMouseMoved(localPoint.getX(), localPoint.getY());
        }
        );
        mainS.setOnMouseDragged(e -> {
            Point2D localPoint = mapPlayAP.sceneToLocal(e.getSceneX(), e.getSceneY());
            gameController.handleMouseMoved(localPoint.getX(), localPoint.getY());
        });

        // Ana köke ekleme yapıyoruz
        mainR.getChildren().setAll(fullBackground, gameRC, gameHUD.getContainer());
        primaryS.show();
    }
    //Kazandın ekranını basar
    public void triggerWinOverlay(StackPane gameRoot, int currentLevel) {
        WinScreen winS = new WinScreen(this, currentLevel);

        winS.prefWidthProperty().bind(mainS.widthProperty());
        winS.prefHeightProperty().bind(mainS.heightProperty());

        mainR.getChildren().add(winS);
    }
    //Kaybettin ekranını basar
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