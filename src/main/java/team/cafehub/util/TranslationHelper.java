package team.cafehub.util;

import org.springframework.stereotype.Component;

@Component
public class TranslationHelper {
    public String getTranslated(String language, String ukValue, String enValue) {
        if ("en".equalsIgnoreCase(language) && enValue != null && !enValue.isBlank()) {
            return enValue;
        }
        return ukValue;
    }
}
