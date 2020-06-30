package ru.ezhov.translator.core;

public enum TranslateLang {
    RU_EN("ru-en"), EN_RU("en-ru");

    private final String lang;

    TranslateLang(String lang) {
        this.lang = lang;
    }

    public static TranslateLang from(String lang) {
        for (TranslateLang translateLang : TranslateLang.values()) {
            if (translateLang.lang.equals(lang)) {
                return translateLang;
            }
        }
        return null;
    }

    public String getLang() {
        return lang;
    }
}
