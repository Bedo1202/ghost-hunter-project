package com.ghosthunter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
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
    
    private SceneManager sceneManager;
    private Pane playAreaPane;
    private GameHUD gameHUD;
    private StackPane gameRoot;
    private Hunter hunter;
    private List<Entity> entities;
    private List<Token> tokens;
    private AnimationTimer animationTimer;
    private Timeline secondTimer;
    private Level activeLevel;
    private int damageCooldownFrames = 0;
    private boolean gameEnded = false;
    private boolean paused = false;
    private int tokenSpawnCounter = 0;
    
    public GameController() {
        // Sınıfın yapılandırıcısıdır, oyun ilk açıldığında bu kod devreye girer.
        this.config = ConfigReader.readConfig("config.txt");
        this.currentLevel = 1; // Oyun 1. seviyeden başlar [cite: 159]
        this.score = 0;
        this.isGameRunning = false;
        
        // oyuncunun o anki değerlerini config'den alıyoruz
        this.currentHealth = config.maximumHealth; 
    }
    public GameController(SceneManager sceneManager, Pane playAreaPane, GameHUD gameHUD, StackPane gameRoot) {
        this();

        this.sceneManager = sceneManager;
        this.playAreaPane = playAreaPane;
        this.gameHUD = gameHUD;
        this.gameRoot = gameRoot;
    }

    // Oyunda seviyeleri başlatan metod.
    public void startLevel(int levelNumber) {
        this.currentLevel = levelNumber;
        this.score = 0;
        this.isGameRunning = true;
        this.gameEnded = false;
        //Seviyeye göre süreyi configden çekiyoruz.
        if (levelNumber == 1) {
            activeLevel = config.level1;
        } else if (levelNumber == 2) {
            activeLevel = config.level2;
        } else {
            activeLevel = config.level3;
        }

        remainingTime = activeLevel.süreSınırı;

        setupObjects();
        updateHUD();
        startSecondTimer();
        startGameLoop();
    }

    // Oyunu genel olarak setuplayan metod.
    private void setupObjects() {
    	playAreaPane.getChildren().removeIf(node -> !(node instanceof ImageView));

        double minX = activeLevel.areaX;
        double minY = activeLevel.areaY;
        double maxX = activeLevel.areaX + activeLevel.width;
        double maxY = activeLevel.areaY + activeLevel.height;

        hunter = new Hunter((minX + maxX) / 2, (minY + maxY) / 2);
        playAreaPane.getChildren().add(hunter.getView());

        entities = EntityFactory.createEntities(
                activeLevel.hayaletSayısı,
                activeLevel.ripperCount,
                activeLevel.wispCount,
                minX,
                minY,
                maxX,
                maxY
        );

        for (Entity entity : entities) {
            playAreaPane.getChildren().add(entity.getView());
        }

        tokens = new ArrayList<>();

        
    }
    //gameloop başlatır.
    private void startGameLoop() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isGameRunning) {
                    updateGame();
                }
            }
        };

        animationTimer.start();
    }
    private void updateGame() {
    	if (paused) {
    	    return;
    	}
        hunter.update();

        double minX = activeLevel.areaX;
        double minY = activeLevel.areaY;
        double maxX = activeLevel.areaX + activeLevel.width;
        double maxY = activeLevel.areaY + activeLevel.height;

        hunter.keepInsideBounds(minX, minY, maxX, maxY);

        if (damageCooldownFrames > 0) {
            damageCooldownFrames--;
        }

        updateEntities(minX, minY, maxX, maxY);
        updateTokens();
        spawnTokensOverTime(minX, minY, maxX, maxY);
        updateHUD();
        checkGameEnd();
    }
    private void updateEntities(double minX, double minY, double maxX, double maxY) {
        for (Entity entity : entities) {
            if (entity.isCaptured()) {
                continue;
            }

            entity.update();
            entity.checkBounds(minX, minY, maxX, maxY);

            boolean visible = hunter.isEyeModeActive()
                    || CollisionUtil.isEntityInsideVacuumCone(hunter, entity);
            entity.setVisible(visible);

            if (CollisionUtil.canVacuumEntity(hunter, entity)) {
                boolean wasCaptured = entity.isCaptured();

                entity.shrink();

                if (!wasCaptured && entity.isCaptured()) {
                    addScore(entity.getScoreValue());
                }
            }

            if (CollisionUtil.isHunterCollidingWithEntity(hunter, entity)
                    && damageCooldownFrames == 0) {

                hunter.takeDamage(config.entityDamage);
                damageCooldownFrames = 60;
            }
        }
    }
    private void updateTokens() {
        for (Token token : tokens) {
            if (token.isActive()
                    && CollisionUtil.isHunterCollidingWithToken(hunter, token)) {

                TokenEffectUtil.applyTokenToHunter(token, hunter);
            }
        }
    }
    private void startSecondTimer() {
        if (secondTimer != null) {
            secondTimer.stop();
        }

        secondTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!isGameRunning) {
                return;
            }

            remainingTime--;

            if (remainingTime <= 0) {
                remainingTime = 0;
                loseGame();
            }

            updateHUD();
        }));

        secondTimer.setCycleCount(Timeline.INDEFINITE);
        secondTimer.play();
    }
 //oyunun durdurulmasını sağlar
    public void stopGame(String reason) {
        this.isGameRunning = false;
        System.out.println("Oyun Durdu: " + reason);

        if (animationTimer != null) {
            animationTimer.stop();
        }

        if (secondTimer != null) {
            secondTimer.stop();
        }
    }
    public void handleMouseMoved(double mouseX, double mouseY) {
        if (hunter != null) {
            hunter.setMousePosition(mouseX, mouseY);
        }
    }	
    //her hayalet yakalandığında skor artar
    public void addScore(int points) {
        this.score += points;
    }
    public void handleInput(KeyCode code, boolean pressed) {
    	if (code == KeyCode.ESCAPE && pressed) {
    	    togglePause();
    	    return;
    	}
        if (hunter == null) {
            return;
        }

        if (code == KeyCode.W || code == KeyCode.UP) {
            hunter.setMovingUp(pressed);
        } else if (code == KeyCode.S || code == KeyCode.DOWN) {
            hunter.setMovingDown(pressed);
        } else if (code == KeyCode.A || code == KeyCode.LEFT) {
            hunter.setMovingLeft(pressed);
        } else if (code == KeyCode.D || code == KeyCode.RIGHT) {
            hunter.setMovingRight(pressed);
        } else if (code == KeyCode.SPACE) {
            hunter.setScanning(pressed);
        }
    }
    private void updateHUD() {
        if (gameHUD == null || hunter == null) {
            return;
        }

        double hp = hunter.getHealth() / (double) hunter.getMaxHealth();
        double vacuum = hunter.getVacuumEnergy() / (double) hunter.getMaxVacuumEnergy();

        gameHUD.syncValues(score, formatTime(remainingTime), hp, vacuum);
    }
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%d:%02d", minutes, seconds);
    }
    private void checkGameEnd() {
        if (!hunter.isAlive()) {
            loseGame();
            return;
        }

        boolean allCaptured = true;

        for (Entity entity : entities) {
            if (!entity.isCaptured()) {
                allCaptured = false;
                break;
            }
        }

        if (allCaptured) {
            winGame();
        }
    }
    private void winGame() {
        if (gameEnded) {
            return;
        }

        gameEnded = true;
        stopGame("You win!");
        sceneManager.triggerWinOverlay(gameRoot, currentLevel);
    }
    private void loseGame() {
        if (gameEnded) {
            return;
        }

        gameEnded = true;
        stopGame("Game over!");
        sceneManager.triggerLoseOverlay(gameRoot, currentLevel, score);
    }
    public void togglePause() {
        if (paused) {
            return;
        }

        paused = true;

        if (secondTimer != null) {
            secondTimer.pause();
        }

        PauseView overlay = new PauseView(sceneManager, this, currentLevel);
        gameRoot.getChildren().add(overlay);
    }

    public void resumeGame() {
        paused = false;

        if (secondTimer != null) {
            secondTimer.play();
        }
    }	
    private void spawnTokensOverTime(double minX,
            double minY,
            double maxX,
            double maxY) {

tokenSpawnCounter++;

// yaklaşık her 9 saniyede bir dene
if (tokenSpawnCounter < 540) {
return;
}

tokenSpawnCounter = 0;

// aktif token sayısı limiti
int activeCount = 0;	
for (Token token : tokens) {
if (token.isActive()) {
activeCount++;
}
}

if (activeCount >= 2) {
return;
}

Token token = TokenFactory.createRandomToken(
minX,
minY,
maxX,
maxY,
config.healthTokenIncrease,
config.vacuumTokenIncrease,
config.eyeTokenDuration * 60
);

tokens.add(token);

playAreaPane.getChildren().add(token.getView());
}
}