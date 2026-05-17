package com.ghosthunter;
//150124056/AHMET KAYRA DEMİRCİ
//tokenların etki metodunun olduğu mini sınıf
public class TokenEffectUtil {
    private TokenEffectUtil() {}
    public static void applyTokenToHunter(Token token, Hunter hunter) {
        if (token == null || hunter == null || !token.isActive()) {
            return;
        }
        if (token instanceof HealthToken) {
            HealthToken healthToken = (HealthToken) token;
            hunter.applyHealthToken(healthToken.getHealthIncrease());
        }
        else if (token instanceof RangeToken) {
            RangeToken rangeToken = (RangeToken) token;
            hunter.applyRangeToken(rangeToken.getRangeIncrease());
        }
        else if (token instanceof EyeToken) {
            EyeToken eyeToken = (EyeToken) token;
            hunter.applyEyeToken(eyeToken.getVisibilitySuresi());
        }
        token.deactivate();
    }
}