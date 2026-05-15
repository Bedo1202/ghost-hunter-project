package com.ghosthunter;
//Bedirhan_Acarturk_150124052

//Bazı sabit değişkenleri içerir ve tekrara düşmeyi önler.
public class Constants {

    //config.txt dosyasını final değişken yapar 
    public static final String CONFIG_FILE = "config.txt"; 
    
   //health, vacuum ve score değişkenlerini final yapar.
    public static final String HEALTH_LABEL = "HEALTH:"; 
    public static final String VACUUM_LABEL = "VACUUM:";
    public static final String SCORE_LABEL = "SCORE: "; 
    
   
    public static final String WIN_MSG = "YOU WIN!"; 
    public static final String WIN_SUB_MSG = "YOU'VE VACUUMED ALL THE GHOSTS!"; 
    public static final String LOSE_MSG = "GAME OVER"; 
    
  
    public static final String HEALTH_COLOR = "#FF0000"; // Canli Kirmizi [cite: 32]
    public static final String VACUUM_COLOR = "#800080"; // Mor [cite: 37]
    
   
    public static final String LEVEL1_THEME = "Deep Purples"; 
    public static final String LEVEL2_THEME = "Cold Teals"; 
    public static final String LEVEL3_THEME = "Aggressive Oranges"; 
}