package com.ghosthunter;
//150124056/AHMET KAYRA DEMİRCİ
//tokenların random yerlerde çıkmasını sağlayan sınıf
import java.util.Random;
public class TokenFactory {
    private static Random random = new Random();   
    public static Token createRandomToken(double minX, double minY, double maxX, double maxY) {
        double x = randomBetween(minX, maxX);
        double y = randomBetween(minY, maxY);
        int tokenType = random.nextInt(3);
        if (tokenType == 0) {
            return new HealthToken(x, y, 20);
        }
        if (tokenType == 1) {
            return new RangeToken(x, y, 20);
        }
        return new EyeToken(x, y, 300);
    }
    public static Token createRandomToken(double minX, double minY, double maxX, double maxY,int healthIncrease, int rangeIncrease, int eyeDuration) {
        double x = randomBetween(minX, maxX);
        double y = randomBetween(minY, maxY);

        int tokenType = random.nextInt(3);

        if (tokenType == 0) {
            return new HealthToken(x, y, healthIncrease);
        }

        if (tokenType == 1) {
            return new RangeToken(x, y, rangeIncrease);
        }

        return new EyeToken(x, y, eyeDuration);
    }

    private static double randomBetween(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }
}