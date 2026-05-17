package com.ghosthunter;

public class CollisionUtil {
public static void main(String[] args) {
	
}

    private CollisionUtil() {
        // utility class olduğu için obje oluşturmuyoruz
    }

    public static boolean isCircleColliding(double x1, double y1, double radius1, double x2, double y2, double radius2){
        double dx = x1 - x2;
        double dy = y1 - y2;
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = radius1 + radius2;

        return distanceSquared <= radiusSum * radiusSum;
    }

    public static boolean isHunterCollidingWithEntity(Hunter hunter, Entity entity) {
        return isCircleColliding(hunter.getX(), hunter.getY(),hunter.getCollisionRadius(),entity.getX(),entity.getY(),entity.getCollisionRadius());
    }

    public static boolean isHunterCollidingWithToken(Hunter hunter, Token token) {
        return isCircleColliding(hunter.getX(), hunter.getY(),hunter.getCollisionRadius(),token.getX(),token.getY(),token.getCollisionRadius());
    }

    public static boolean isEntityInsideScanRange(Hunter hunter, Entity entity) {
        return isCircleColliding(hunter.getX(), hunter.getY(),hunter.getScanRange(),entity.getX(),entity.getY(),entity.getCollisionRadius());
    }
 // Scanner açıkken ve entity scan range içindeyken vacuum etkisi uygulanabilir.
 // GameController hazır olunca bu method update loop içinde kullanılacak.
    public static boolean canVacuumEntity(Hunter hunter, Entity entity) {
    	return hunter.isScanning() && !entity.isCaptured() && isEntityInsideVacuumCone(hunter, entity); }
    public static boolean isEntityInsideVacuumCone(Hunter hunter, Entity entity) {
        double hx = hunter.getX();
        double hy = hunter.getY();

        double mx = hunter.getMouseX();
        double my = hunter.getMouseY();

        double ex = entity.getX();
        double ey = entity.getY();

        double dx = mx - hx;
        double dy = my - hy;

        double distanceToMouse = Math.sqrt(dx * dx + dy * dy);

        if (distanceToMouse == 0) {
            return false;
        }

        double dirX = dx / distanceToMouse;
        double dirY = dy / distanceToMouse;

        double entityX = ex - hx;
        double entityY = ey - hy;

        double forwardDistance = entityX * dirX + entityY * dirY;

        if (forwardDistance < 0 || forwardDistance > hunter.getScanRange()) {
            return false;
        }

        double perpendicularDistance = Math.abs(entityX * (-dirY) + entityY * dirX);

        double coneHalfWidth = hunter.getConeHalfWidth();

        return perpendicularDistance <= coneHalfWidth + entity.getCollisionRadius();
    }
} 