package com.ghosthunter;
//150124056/AHMET KAYRA DEMİRCİ
import javafx.scene.Node;
//tüm tokenlar için abstract class
public abstract class Token {
    double x;
    double y;
    boolean active = true;
    public Token(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public abstract Node getView();
  // GameController token alınınca deactivate() çağıracak
    public void deactivate() {
        active = false;
        getView().setVisible(false);
    }
 // Token toplandı mı kontrolünde kullanılacak
 public boolean isCollected() {
     return !active;
 }
    public boolean isActive() {
        return active;
    }
    public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getCollisionRadius() {
        return 15;
    }
}