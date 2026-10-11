package com.ghosthunter;
//Bedirhan_Acarturk_150124052

public class EngineTest {
    public static void main(String[] args) {
        System.out.println("--- ENGINE TESTI BASLIYOR ---");

        //  Controller'i olustur. BU kod configi okur.
        GameController controller = new GameController();

        // Config dosyasının okunup okunmadığını kontrol eder. 
        System.out.println("Okunan Max Can: " + controller.config.maximumHealth);
        System.out.println("Level 2 Sure: " + controller.config.level2.süreSınırı);

        //  Seviye 2'yi baslat ve sayaci goster
        System.out.println("Level 2 baslatiliyor...");
        controller.startLevel(1);
    }
}