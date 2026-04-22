package com.ghosthunter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Butonlar
        Button startButton = new Button("Start Game");
        Button levelButton = new Button("Select Level");
        Button exitButton = new Button("Exit");

        // Basit aksiyonlar
        startButton.setOnAction(e -> System.out.println("Start Game clicked"));
        levelButton.setOnAction(e -> System.out.println("Select Level clicked"));
        exitButton.setOnAction(e -> primaryStage.close());

        // Layout
        VBox root = new VBox(20);
        root.getChildren().addAll(startButton, levelButton, exitButton);
        root.setStyle("-fx-alignment: center; -fx-background-color: black;");

        // Scene
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Ghost Hunter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
