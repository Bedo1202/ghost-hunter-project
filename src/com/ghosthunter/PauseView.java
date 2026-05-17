package com.ghosthunter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PauseView extends StackPane {

    public PauseView(SceneManager sceneManager,
                        GameController gameController,
                        int currentLevel) {

        Rectangle bg = new Rectangle(1024, 768);
        bg.setFill(Color.rgb(0, 0, 0, 0.6));

        Label title = new Label("GAME PAUSED");
        title.setStyle("""
                -fx-font-size: 42px;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                """);

        Button resumeBtn = new Button("RESUME");
        Button retryBtn = new Button("RETRY");
        Button menuBtn = new Button("MAIN MENU");

        String buttonStyle = """
                -fx-font-size: 20px;
                -fx-background-color: #222;
                -fx-text-fill: white;
                -fx-padding: 10 30 10 30;
                -fx-border-color: white;
                -fx-border-width: 2;
                """;

        resumeBtn.setStyle(buttonStyle);
        retryBtn.setStyle(buttonStyle);
        menuBtn.setStyle(buttonStyle);

        resumeBtn.setOnAction(e -> {
            gameController.resumeGame();
            ((StackPane)getParent()).getChildren().remove(this);
        });

        retryBtn.setOnAction(e -> {
            gameController.stopGame("Retry");
            sceneManager.startLevel(currentLevel);
        });

        menuBtn.setOnAction(e -> {
            gameController.stopGame("Main Menu");
            sceneManager.showMainMenu();
        });

        VBox layout = new VBox(20,
                title,
                resumeBtn,
                retryBtn,
                menuBtn);

        layout.setAlignment(Pos.CENTER);

        getChildren().addAll(bg, layout);
    }
}
