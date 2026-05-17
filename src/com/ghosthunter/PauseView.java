package com.ghosthunter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
public class PauseView extends StackPane {

    public PauseView(SceneManager sceneManager,
                        GameController gameController,
                        int currentLevel) {

    	Rectangle bg = new Rectangle();
    	bg.widthProperty().bind(widthProperty());
    	bg.heightProperty().bind(heightProperty());
    	bg.setFill(Color.rgb(0, 0, 0, 0.6));

        Label title = new Label("GAME PAUSED");
        Font spookyTitleFont = Font.loadFont(
                getClass().getResourceAsStream("Creepster-Regular.ttf"),
                48
        );

        if (spookyTitleFont == null) {
            spookyTitleFont = Font.font("Arial", 48);
        }

        title.setFont(spookyTitleFont);
        title.setTextFill(Color.WHITE);

        Button resumeBtn = new Button("RESUME");
        Button retryBtn = new Button("RETRY");
        Button menuBtn = new Button("MAIN MENU");
        Font spookyButtonFont = Font.loadFont(
                getClass().getResourceAsStream("Creepster-Regular.ttf"),
                24
        );

        if (spookyButtonFont == null) {
            spookyButtonFont = Font.font("Arial", 24);
        }

        resumeBtn.setFont(spookyButtonFont);
        retryBtn.setFont(spookyButtonFont);
        menuBtn.setFont(spookyButtonFont);
        stylePauseButton(resumeBtn);
        stylePauseButton(retryBtn);
        stylePauseButton(menuBtn);

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
    private void stylePauseButton(Button btn) {
        btn.setFont(Font.loadFont(
                getClass().getResourceAsStream("Creepster-Regular.ttf"),
                24
        ));

        if (btn.getFont() == null) {
            btn.setFont(Font.font("Arial", 24));
        }

        btn.setPrefSize(180, 40);

        btn.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(Color.web("#9400D3"), null, null)
        ));

        btn.setTextFill(Color.WHITE);

        btn.setOnMouseEntered(e -> {
            btn.setBackground(new javafx.scene.layout.Background(
                    new javafx.scene.layout.BackgroundFill(Color.WHITE, null, null)
            ));
            btn.setTextFill(Color.RED);
        });

        btn.setOnMouseExited(e -> {
            btn.setBackground(new javafx.scene.layout.Background(
                    new javafx.scene.layout.BackgroundFill(Color.web("#9400D3"), null, null)
            ));
            btn.setTextFill(Color.WHITE);
        });

        btn.setOnMousePressed(e -> {
            btn.setBackground(new javafx.scene.layout.Background(
                    new javafx.scene.layout.BackgroundFill(Color.WHITE, null, null)
            ));
            btn.setTextFill(Color.RED);
        });

        btn.setOnMouseReleased(e -> {
            btn.setBackground(new javafx.scene.layout.Background(
                    new javafx.scene.layout.BackgroundFill(Color.web("#9400D3"), null, null)
            ));
            btn.setTextFill(Color.WHITE);
        });
    }
}
