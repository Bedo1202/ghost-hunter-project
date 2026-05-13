package com.ghosthunter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityFactory {

    private static final Random random = new Random();

    private EntityFactory() {
        // Factory class. Obje oluşturmaya gerek yok.
    }

    public static Ghost createRandomGhost(double minX, double minY, double maxX, double maxY) {
        double x = randomBetween(minX, maxX);
        double y = randomBetween(minY, maxY);

        return new Ghost(x, y);
    }

    public static Ripper createRandomRipper(double minX, double minY, double maxX, double maxY) {
        double x = randomBetween(minX, maxX);
        double y = randomBetween(minY, maxY);

        return new Ripper(x, y);
    }

    public static Wisp createRandomWisp(double minX, double minY, double maxX, double maxY) {
        double x = randomBetween(minX, maxX);
        double y = randomBetween(minY, maxY);

        return new Wisp(x, y);
    }

    // Engine branch level dosyasından gelen sayılara göre burayı çağırabilir.
    public static List<Entity> createEntities(int ghostCount, int ripperCount, int wispCount,
                                              double minX, double minY, double maxX, double maxY) {
        List<Entity> entities = new ArrayList<>();

        for (int i = 0; i < ghostCount; i++) {
            entities.add(createRandomGhost(minX, minY, maxX, maxY));
        }

        for (int i = 0; i < ripperCount; i++) {
            entities.add(createRandomRipper(minX, minY, maxX, maxY));
        }

        for (int i = 0; i < wispCount; i++) {
            entities.add(createRandomWisp(minX, minY, maxX, maxY));
        }

        return entities;
    }

    private static double randomBetween(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }
}
