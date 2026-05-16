package com.ghosthunter;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Department of Computer Science & Engineering - CSE 1242 Term Project [cite: 8, 9, 10]
 * Submission Date: 17.05.2026 [cite: 11]
 * Entry class for feature-ui.
 */
public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}