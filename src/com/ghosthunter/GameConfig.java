package com.ghosthunter;
//Bedirhan_Acarturk_150124052
//Projede yer alan tüm değişkenleri tek bir merkezde tutan sınıftır

public class GameConfig {
	
	//global player istatistigi
	public int maximumHealth;
	public int maximumVacuum;
	public int entityDamage;
	public int vacuumDecrease;
	public int vacuumIncrease;
	
	//seviyeler bilgileri
	public Level level1 = new Level();
	public Level level2 = new Level();
	public Level level3 = new Level();
	
	//token ayarları,bu kısımda tokenler için gerekli değişkenleri oluşturdum
	public int healthTokenIncrease;
	public int vacuumTokenIncrease;
	public int eyeTokenDuration;
	
	//Bu kod blogunu oyunun çökmesini önlemek ve bir varsayılan değer oluşturmak için yaptım.
	public GameConfig() {
		this.maximumHealth=100;
		this.maximumVacuum=100;
	}
	
}