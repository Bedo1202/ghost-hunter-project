package com.ghosthunter;
//150124056/Ahmet KayraDemirci
import javafx.scene.Node;
//bütün entityler için abstractımız
public abstract class Entity {
    double x;
    double y;
    double speedX;
    double speedY;
    boolean visible;
    int scoreValue;
    boolean captured;
    protected double shrinkRate = 0.01;
    public Entity(double x, double y, double speedX, double speedY, int scoreValue) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.scoreValue = scoreValue;
        this.visible = false;
    }
    // her düşman kendi şeklini ayrı tutacağı için abstract
    public abstract Node getView();
    // temel hareket metodu
    public void update() {
    	//yakalandıysa update etmiyor
    	if (captured) {
    	    return;
    	}
    	x += speedX;
    	y += speedY;
       updateViewPosition();
    }
 // playable area sınırlarından sekme mantığı
 // bedirhan playable area değerlerini hazır edince bu methodu çağıracak
    public void checkBounds(double minX, double minY, double maxX, double maxY) {
        double radius = getCollisionRadius();
    if (x - radius <= minX) {
            x = minX + radius;
            speedX *= -1;
        }
    if (x + radius >= maxX) {
            x = maxX - radius;
            speedX *= -1;
        }
    if (y - radius <= minY) {
            y = minY + radius;
            speedY *= -1;
    }
    if (y + radius >= maxY) {
            y = maxY - radius;
            speedY *= -1;
    }
        updateViewPosition();
    }
//entity scanner alanında kaldıkça küçülür
//yeterince küçülürse captured true oluyor
 public void shrink() {
	 Node view = getView();

  double newScaleX = view.getScaleX() - shrinkRate;
  double newScaleY = view.getScaleY() - shrinkRate;

  if (newScaleX <= 0.15 || newScaleY <= 0.15) {
      captured = true;
      view.setVisible(false);
      return;
  }
  view.setScaleX(newScaleX);
  view.setScaleY(newScaleY);
}
    protected abstract void updateViewPosition();    
    public void setVisible(boolean visible) {
        this.visible = visible;
        getView().setVisible(visible);
    }
    public void reverseXDirection() {
        speedX *= -1;
    }
    public void reverseYDirection() {
        speedY *= -1;
    }
    public boolean isVisible() {
        return visible;
    }
    public boolean isCaptured() {
        return captured;
    }
    public int getScoreValue() {
        return scoreValue;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getCollisionRadius() {
        return 20;
    }
 // Eye token veya cheat key gibi durumlarda entity yeniden görünür yapılabilir.
    public void resetVisibility() {
        if (!captured) {
        setVisible(false); 
    }
    }
}