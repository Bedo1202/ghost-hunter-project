package com.ghosthunter;

import javafx.scene.Node;

public abstract class Token {
    protected double x;
    protected double y;
    protected boolean active = true;

    public Token(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public abstract Node getView();

    public void deactivate() {
        active = false;
        getView().setVisible(false);
    }
 // Token toplandı mı kontrolünde kullanılacak.
 // GameController token alınınca deactivate() çağıracak.
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
	// Rectangle tokenlarda override etmek gerekebilir.
	public double getCollisionRadius() {
        return 15;
    }
}