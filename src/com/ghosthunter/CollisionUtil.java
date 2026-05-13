package com.ghosthunter;

public class CollisionUtil {

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
    	return hunter.isScanning() && !entity.isCaptured() && isEntityInsideScanRange(hunter, entity); }
} 