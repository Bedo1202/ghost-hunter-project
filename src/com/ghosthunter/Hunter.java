package com.ghosthunter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Hunter {
    private double x;
    private double y;
    private double speed = 4.0;
    private int health = 100;
    private int maxHealth = 100;
    private int vacuumEnergy = 100;
    private int maxVacuumEnergy = 100;
    private int scanRange = 100;
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;
    private String direction;
    private boolean scanning;
    private boolean eyeModeActive;
    private int eyeModeTimer;
    private final Circle view;
	private int damageColorTimer;

    public Hunter(double x, double y) {
        this.x = x;
        this.y = y;

        
    view = new Circle(x, y, 20);
    view.setFill(Color.ORANGE);
    }

    // game loop içinde sürekli çağrılacak main update metodu
    public void update() {
        updateMovement();
        updateVacuumEnergy();
        updateEyeMode();
        updateDamageColor();
        updateViewPosition();
        
    }

    private void updateMovement() {
		if (movingUp) {
    	    y -= speed;
    	    direction = "UP";
    	}

    	if (movingDown) {
    	    y += speed;
    	    direction = "DOWN";
    	}

    	if (movingLeft) {
    	    x -= speed;
    	    direction = "LEFT";
    	}

    	if (movingRight) {
    	    x += speed;
    	    direction = "RIGHT";
    	}
    }

    private void updateVacuumEnergy() {
        if (scanning && vacuumEnergy > 0) {
            vacuumEnergy--;
        } else if (!scanning && vacuumEnergy < maxVacuumEnergy) {
            vacuumEnergy++;
        }

        if (vacuumEnergy <= 0) {
            vacuumEnergy = 0;
            scanning = false;
        }
    }

 private void updateDamageColor() {
     if (damageColorTimer > 0) {
         damageColorTimer--;

         if (damageColorTimer == 0) {
             view.setFill(Color.ORANGE);
         }
     }
 }
    private void updateEyeMode() {
        if (!eyeModeActive) {
            return;
        }

        eyeModeTimer--;

        if (eyeModeTimer <= 0) {
            eyeModeActive = false;
            eyeModeTimer = 0;
        }
    }

    private void updateViewPosition() {
        view.setCenterX(x);
        view.setCenterY(y);
    }

    public void takeDamage(int amount) {
        health -= amount;

        if (health < 0) {
            health = 0;
        }

        // hasar alınca geçici olarak kırmızıya dönmesi için
        view.setFill(Color.RED);
        damageColorTimer = 20;
    }

    public void applyHealthToken(int amount) {
        health += amount;

        if (health > maxHealth) {
            health = maxHealth;
        }

        view.setFill(Color.ORANGE);
    }

    public void applyRangeToken(int amount) {
        scanRange += amount;
    }

    public void applyEyeToken(int duration) {
        eyeModeActive = true;
        eyeModeTimer = duration;
    }
 // Engine branch playable area sınırlarını gönderince hunter dışarı çıkmasın diye kullanılacak.
    public void keepInsideBounds(double minX, double minY, double maxX, double maxY) {
        double radius = getCollisionRadius();

        if (x - radius < minX) {
            x = minX + radius;
        }

        if (x + radius > maxX) {
            x = maxX - radius;
        }

        if (y - radius < minY) {
            y = minY + radius;
        }

        if (y + radius > maxY) {
            y = maxY - radius;
        }
    }

    public Node getView() {
        return view;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public String getDirection() {
		return direction;
	}
    public boolean isAlive() {
        return health > 0;
    }
	public double getCollisionRadius() {
        return view.getRadius();
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getVacuumEnergy() {
        return vacuumEnergy;
    }

    public int getMaxVacuumEnergy() {
        return maxVacuumEnergy;
    }

    public int getScanRange() {
        return scanRange;
    }

    public boolean isScanning() {
        return scanning;
    }

    public boolean isEyeModeActive() {
        return eyeModeActive;
    }

    public void setScanning(boolean scanning) {
        if (vacuumEnergy > 0) {
            this.scanning = scanning;
        }
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}