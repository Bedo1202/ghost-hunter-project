package com.ghosthunter;
//Ahmet Kayra Demirci/150124056
//tüm çarpışmaları kontrol eden class
public class CollisionUtil {
    private CollisionUtil() {
        // utility class olduğu için obje oluşturmuyoruz
    }
    //genel circle çarpışma metodu
    public static boolean isCircleColliding(double x1,double y1, double radius1,double x2, double y2,double radius2){
        double dx = x1 - x2;
        double dy = y1 - y2;
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = radius1 + radius2;
        return distanceSquared <= radiusSum * radiusSum;
    }
    //hunterXentity
    public static boolean isHunterCollidingWithEntity(Hunter hunter, Entity entity) {
        return isCircleColliding(hunter.getX(), hunter.getY(),hunter.getCollisionRadius(),entity.getX(),entity.getY(),entity.getCollisionRadius());
    }
    //hunterXtoken
    public static boolean isHunterCollidingWithToken(Hunter hunter, Token token) {
        return isCircleColliding(hunter.getX(), hunter.getY(),hunter.getCollisionRadius(),token.getX(),token.getY(),token.getCollisionRadius());
    }
    //entityler scan rangede mi
    public static boolean isEntityInsideScanRange(Hunter hunter, Entity entity) {
        return isCircleColliding(hunter.getX(), hunter.getY(),hunter.getScanRange(),entity.getX(),entity.getY(),entity.getCollisionRadius());
    }
 // scanner açıkken ve entity scan range içindeyken vacuum etkisi uygulanabilir.
 // GameController hazır olunca bu method update loop içinde kullanılacak.
    public static boolean canVacuumEntity(Hunter hunter, Entity entity) {
    	return hunter.isScanning() && !entity.isCaptured() && isEntityInsideVacuumCone(hunter, entity); }
    
    public static boolean isEntityInsideVacuumCone(Hunter hunter, Entity entity) {
    	// hunter,mouse ve entity koordinatlarını alıyoruz
        double hx = hunter.getX();
        double hy = hunter.getY();
        double mx = hunter.getMouseX();
        double my = hunter.getMouseY();
        double ex = entity.getX();
        double ey = entity.getY();
        //mouse yönü için variablelar
        double dx = mx - hx;
        double dy = my - hy;
        //mouse uzaklığı
        double distanceToMouse = Math.sqrt(dx * dx + dy * dy);
        
        if (distanceToMouse == 0) {
            return false;
        }
     // bu işlemlerle elimizde birim uzunlukta yön kalkyor
        double dirX = dx / distanceToMouse;
        double dirY = dy / distanceToMouse;
        //entity konumu
        double entityX = ex - hx;
        double entityY = ey - hy;
     // dot product kullanarak entitynin hunterın baktığı yönde ne kadar ileride olduğunu hesaplıyoruz
        double forwardDistance = entityX * dirX + entityY * dirY;
        //range kontrolü
        if (forwardDistance < 0 || forwardDistance > hunter.getScanRange()) {
            return false;
        }
     // Entitynin vacuum yön çizgisine olan dik uzaklığı
        double perpendicularDistance = Math.abs(entityX * (-dirY) + entityY * dirX);
        double coneHalfWidth = hunter.getConeHalfWidth();
        return perpendicularDistance <= coneHalfWidth + entity.getCollisionRadius();
    }
} 