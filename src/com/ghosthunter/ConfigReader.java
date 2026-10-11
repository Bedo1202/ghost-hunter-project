package com.ghosthunter;

import java.io.File;
import java.util.Scanner;

//Bedirhan_Acarturk_150124052
//bu classı congig.txt doyasını okuyabilmek için oluşturdum.
public class ConfigReader {
	
	//bakılması gereken dosyayı gösterir ve varsayılan değerlerle şimdilik bir nesne oluşturur.
	public static GameConfig readConfig(String filePath) {
	GameConfig config = new GameConfig();	
	File dosya = new File(filePath);
	
	//Dosyadaki stringleri okuyup integera çevirir ve bu esnada dosyanın boş olma ihtimaline karşılık exceptions fırlatır.
	try (Scanner scanner= new Scanner(dosya)){
		while(scanner.hasNextLine()) {
			String line= scanner.nextLine().trim();
			if(line.isEmpty() || !line.contains(":"))continue;
			
			String[] parts = line.split(":");
            String key = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            
            assignToConfig(config, key, value);
		}
		
	} catch (Exception e) {
		System.out.println("KONFİGÜRASYON DOSYASI OKUNURKEN HATA OLUŞTU" + e.getMessage());
		
	}
	return config;
	}
	private static void assignToConfig(GameConfig config, String key, int value) {
        switch (key) {
            // dosyadaki string halindeki verileri GameConfig classındaki gerçek değişkenlere eşitler.
            case "maximum_health": config.maximumHealth = value; break;
            case "maximum_vacuum": config.maximumVacuum = value; break;
            case "entity_damage": config.entityDamage = value; break;
            case "vacuum_decrease": config.vacuumDecrease = value; break;
            case "vacuum_increase": config.vacuumIncrease = value; break;

            // Seviye 1 parametrelerini dosyadan okuyup GameConfig sınıfındaki değişkenlere eşitler.
            case "level_1_playable_area_x": config.level1.areaX = value; break;
            case "level_1_playable_area_y": config.level1.areaY = value; break;
            case "level_1_playable_area_width": config.level1.width = value; break;
            case "level_1_playable_area_height": config.level1.height = value; break;
            case "level_1_time": config.level1.süreSınırı = value; break;
            case "level_1_ghosts": config.level1.hayaletSayısı = value; break;
            case "level_1_rippers": config.level1.ripperCount = value; break;
            case "level_1_wisps": config.level1.wispCount = value; break;

            // Seviye 2 parametrelerini dosyadan okuyup GameConfig sınıfındaki değişkenlere eşitler.
            case "level_2_playable_area_x": config.level2.areaX = value; break;
            case "level_2_playable_area_y": config.level2.areaY = value; break;
            case "level_2_playable_area_height": config.level2.height = value; break;
            case "level_2_wisps": config.level2.wispCount = value; break;
            case "level_2_playable_area_width": config.level2.width = value; break;
            case "level_2_time": config.level2.süreSınırı = value; break;
            case "level_2_ghosts": config.level2.hayaletSayısı = value; break;
            case "level_2_rippers": config.level2.ripperCount = value; break;

            // Seviye 3 parametrelerini dosyadan okuyup GameConfig sınıfındaki değişkenlere eşitler.
            case "level_3_playable_area_x": config.level3.areaX = value; break;
            case "level_3_playable_area_y": config.level3.areaY = value; break;
            case "level_3_playable_area_width": config.level3.width = value; break;
            case "level_3_playable_area_height": config.level3.height = value; break;
            case "level_3_time": config.level3.süreSınırı = value; break;
            case "level_3_ghosts": config.level3.hayaletSayısı = value; break;
            case "level_3_rippers": config.level3.ripperCount = value; break;
            case "level_3_wisps": config.level3.wispCount = value; break;

            //Token parametrelerini dosyadan okuyup GameConfig sınıfındaki değişkenlere eşitler.
            case "health_token_increase": config.healthTokenIncrease = value; break;
            case "vacuum_token_increase": config.vacuumTokenIncrease = value; break;
            case "eye_token_duration": config.eyeTokenDuration = value; break;
        }
    }

}