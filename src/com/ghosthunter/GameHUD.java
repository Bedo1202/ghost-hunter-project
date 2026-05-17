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
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
/**
 * Grup Projesi - GameHUD Sınıfı
 * Amaç: PDF Sayfa 3'teki oyun içi HUD yerleşimi (Sol Vakum, Sağ Can, Üst Skor/Süre)[cite: 28, 29].
 */
public class GameHUD {

    private final BorderPane hudContainer;
    private Label scoreLabel;
    private Label timerLabel;
    private Rectangle healthFill;
    private Rectangle vacuumFill;

    private final double barWidth = 34;
    private final double barHeight = 220;
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
        scoreLabel.setFont(Font.loadFont(
                getClass().getResourceAsStream("Creepster-Regular.ttf"),
                22));
        scoreLabel.setTextFill(Color.WHITE);

        timerLabel = new Label("1:20"); // MM:SS formatı dökümandan alındı
        timerLabel.setFont(Font.loadFont(
                getClass().getResourceAsStream("Creepster-Regular.ttf"),
                26));
        timerLabel.setTextFill(Color.WHITE);

        topBox.getChildren().addAll(scoreLabel, timerLabel);
        hudContainer.setTop(topBox);
        BorderPane.setAlignment(topBox, Pos.CENTER);
    }

    private void initLeftVacuumHUD() {
        VBox leftBox = new VBox(5);
        leftBox.setAlignment(Pos.TOP_CENTER);

        Label vacuumTitle = new Label("VACUUM:");
        vacuumTitle.setFont(Font.loadFont(
                getClass().getResourceAsStream("Creepster-Regular.ttf"),
                16));
        vacuumTitle.setTextFill(Color.WHITE);

        Rectangle vacuumBackground = new Rectangle(barWidth, barHeight -7);
        vacuumBackground.setFill(Color.BLACK);
        vacuumBackground.setStroke(Color.WHITE);
        vacuumBackground.setStrokeWidth(3);
        vacuumBackground.setArcWidth(12);
        vacuumBackground.setArcHeight(12);

        vacuumFill = new Rectangle(barWidth -4, barHeight -4);
        vacuumFill.setFill(Color.PURPLE);
        vacuumFill.setArcWidth(8);
        vacuumFill.setArcHeight(8);

        StackPane vacuumStack = new StackPane(vacuumBackground, vacuumFill);
        vacuumStack.setAlignment(Pos.BOTTOM_CENTER);
        vacuumStack.setPadding(new Insets(4));

        VBox barWrapper = new VBox(vacuumStack);
        barWrapper.setPadding(new Insets(10, 0, 0, 0));

        leftBox.getChildren().addAll(vacuumTitle, barWrapper);
        hudContainer.setLeft(leftBox);
    }

    private void initRightHealthHUD() {
        VBox rightBox = new VBox(5);
        rightBox.setAlignment(Pos.TOP_CENTER);

        Label healthTitle = new Label("HEALTH:");
        healthTitle.setFont(Font.loadFont(
                getClass().getResourceAsStream("Creepster-Regular.ttf"),
                16));
        healthTitle.setTextFill(Color.WHITE);

        Rectangle healthBackground = new Rectangle(barWidth, barHeight -7);
        healthBackground.setFill(Color.BLACK);
        healthBackground.setStroke(Color.WHITE);
        healthBackground.setStrokeWidth(3);
        healthBackground.setArcWidth(12);
        healthBackground.setArcHeight(12);

        healthFill = new Rectangle(barWidth -4, barHeight -4);
        healthFill.setFill(Color.RED);
        healthFill.setArcWidth(8);
        healthFill.setArcHeight(8);

        StackPane healthStack = new StackPane(healthBackground, healthFill);
        healthStack.setAlignment(Pos.BOTTOM_CENTER);
        healthStack.setPadding(new Insets(4));

        VBox barWrapper = new VBox(healthStack);
        barWrapper.setPadding(new Insets(10, 0, 0, 0));

        rightBox.getChildren().addAll(healthTitle, barWrapper);
        hudContainer.setRight(rightBox);
    }

    public BorderPane getContainer() { return hudContainer; }

    public void syncValues(int score, String timeStr, double hp, double vac) {
        scoreLabel.setText("SCORE: " + score);
        timerLabel.setText(timeStr);

        hp = Math.max(0, Math.min(1, hp));
        vac = Math.max(0, Math.min(1, vac));

        healthFill.setHeight((barHeight - 8) * hp);
        vacuumFill.setHeight((barHeight - 8) * vac);
    }
}