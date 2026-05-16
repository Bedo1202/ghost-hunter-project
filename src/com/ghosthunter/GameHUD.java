package com.ghosthunter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Grup Projesi - GameHUD Sınıfı
 * Amaç: PDF Sayfa 3'teki oyun içi HUD yerleşimi (Sol Vakum, Sağ Can, Üst Skor/Süre)[cite: 28, 29].
 */
public class GameHUD {

    private final BorderPane hudContainer;
    private Label scoreLabel;
    private Label timerLabel;
    private ProgressBar healthBar;
    private ProgressBar vacuumBar;

    public GameHUD() {
        hudContainer = new BorderPane();
        hudContainer.setPadding(new Insets(20));
        hudContainer.setPickOnBounds(false); // Fare tıklamalarını engellemez

        initTopCenterHUD();
        initLeftVacuumHUD();
        initRightHealthHUD();
    }

    private void initTopCenterHUD() {
        VBox topBox = new VBox(2);
        topBox.setAlignment(Pos.CENTER);

        // PDF Sayfa 3: Spooky/Hand-drawn yazı stili
        scoreLabel = new Label("SCORE: 0");
        scoreLabel.setFont(Font.font("Impact", FontWeight.NORMAL, 22));
        scoreLabel.setTextFill(Color.WHITE);

        timerLabel = new Label("1:20"); // MM:SS formatı dökümandan alındı
        timerLabel.setFont(Font.font("Impact", FontWeight.NORMAL, 26));
        timerLabel.setTextFill(Color.WHITE);

        topBox.getChildren().addAll(scoreLabel, timerLabel);
        hudContainer.setTop(topBox);
        BorderPane.setAlignment(topBox, Pos.CENTER);
    }

    private void initLeftVacuumHUD() {
        VBox leftBox = new VBox(5);
        leftBox.setAlignment(Pos.TOP_CENTER);

        Label vacuumTitle = new Label("VACUUM:");
        vacuumTitle.setFont(Font.font("Impact", FontWeight.NORMAL, 16));
        vacuumTitle.setTextFill(Color.WHITE);

        vacuumBar = new ProgressBar(1.0);
        vacuumBar.setPrefSize(35, 220);
        // PDF İsteri: Kalın siyah kenarlık (border) ve canlı mor (purple) dolgu rengi
        vacuumBar.setStyle("-fx-accent: #800080; -fx-control-inner-background: #000000; -fx-box-border: black; -fx-stroke-width: 4px;");
        vacuumBar.setRotate(-90); // Dikey görünüm transformasyonu

        VBox barWrapper = new VBox(vacuumBar);
        barWrapper.setPadding(new Insets(90, 0, 0, 0));

        leftBox.getChildren().addAll(vacuumTitle, barWrapper);
        hudContainer.setLeft(leftBox);
    }

    private void initRightHealthHUD() {
        VBox rightBox = new VBox(5);
        rightBox.setAlignment(Pos.TOP_CENTER);

        Label healthTitle = new Label("HEALTH:");
        healthTitle.setFont(Font.font("Impact", FontWeight.NORMAL, 16));
        healthTitle.setTextFill(Color.WHITE);

        healthBar = new ProgressBar(1.0);
        healthBar.setPrefSize(35, 220);
        // PDF İsteri: Kalın siyah kenarlık (border) ve canlı kırmızı (red) dolgu rengi
        healthBar.setStyle("-fx-accent: #FF0000; -fx-control-inner-background: #000000; -fx-box-border: black; -fx-stroke-width: 4px;");
        healthBar.setRotate(-90);

        VBox barWrapper = new VBox(healthBar);
        barWrapper.setPadding(new Insets(90, 0, 0, 0));

        rightBox.getChildren().addAll(healthTitle, barWrapper);
        hudContainer.setRight(rightBox);
    }

    public BorderPane getContainer() { return hudContainer; }

    public void syncValues(int score, String timeStr, double hp, double vac) {
        scoreLabel.setText("SCORE: " + score);
        timerLabel.setText(timeStr);
        healthBar.setProgress(hp);
        vacuumBar.setProgress(vac);
    }
}