package com.ghosthunter;

public class MechanicsTest {

    public static void main(String[] args) {
        Hunter hunter = new Hunter(100, 100);
        Ghost ghost = new Ghost(110, 110);

        System.out.println("Hunter health: " + hunter.getHealth());
        System.out.println("Ghost score: " + ghost.getScoreValue());

        boolean collision = CollisionUtil.isHunterCollidingWithEntity(hunter, ghost);
        System.out.println("Hunter and ghost collision: " + collision);

        HealthToken healthToken = new HealthToken(100, 100, 20);
        TokenEffectUtil.applyTokenToHunter(healthToken, hunter);

        System.out.println("Hunter health after token: " + hunter.getHealth());
        System.out.println("Health token active: " + healthToken.isActive());

        System.out.println("Mechanics test finished.");
    }
}