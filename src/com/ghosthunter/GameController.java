package com.ghosthunter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController {
    // oyunun o anki durumu için gerekli değişkenler.
    public GameConfig config;
    public int currentLevel;
    public int score;
    public int remainingTime;
    public double currentHealth;
    
    // Oyunun o anki durumunu kontrol etmek için basit değişkenler
    public boolean isGameRunning;
    private KeyFrame gameLoop;

    public GameController() {
        // Sınıfın yapılandırıcısıdır, oyun ilk açıldığında bu kod devreye girer.
        this.config = ConfigReader.readConfig("config.txt");
        this.currentLevel = 1; // Oyun 1. seviyeden başlar [cite: 159]
        this.score = 0;
        this.isGameRunning = false;
        
        // oyuncunun o anki değerlerini config'den alıyoruz
        this.currentHealth = config.maximumHealth; 
    }

    // Oyunda seviyeleri başlatan metod.
    public void startLevel(int levelNumber) {
        this.currentLevel = levelNumber;
        this.isGameRunning = true;

        // Seviyeye göre süreyi config'den çekiyoruz [cite: 241]
        if (levelNumber == 1) {
            remainingTime =config.level1.timeLimit;
        } else if (levelNumber == 2) {
            remainingTime = config.level2.timeLimit;
        } else {
            remainingTime =config.level3.timeLimit;
        }

        startTimer();
    }

    // Oyunu geri sayım sürecini tamamlar
    private void startTimer() {
    	//Zamanlayıcının 1 saniyede bir harekete geçmesini sağlar.
        gameLoop = new KeyFrame(Duration.seconds(1), event -> {
            if (remainingTime > 0 && isGameRunning) {
                remainingTime--;
                System.out.println("Kalan Süre: " + remainingTime);
            } else {
                stopGame("Time is Up!"); 
            }
        });
        // durdurulana kadar sayacın sürekli çalışmasını sağlar
        Timeline timeline = new Timeline(gameLoop);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
 //oyunun durdurulmasını sağlar
    public void stopGame(String reason) {
        this.isGameRunning = false;
        System.out.println("Oyun Durdu: " + reason);
        
    }
    
    //her hayalet yakalandığında skor artar
    public void addScore(int points) {
        this.score += points;
    }
}