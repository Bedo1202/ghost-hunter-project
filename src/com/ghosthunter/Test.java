package com.ghosthunter;

import javafx.application.Application;
import javafx.stage.Stage;

//Ali_Yüşa_Sezer_150124071
//Arkada pencere açar
public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
    	SceneManager sceneManager = new SceneManager(primaryStage);
        
        //Tam ekran yapar
        primaryStage.setFullScreen(true); 
        
        // Ana menüyü çağırır
        sceneManager.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}