package ru.ezhov.translator.gui.translate;

public enum TranslateLang {
    RU_EN("ru-en"), EN_RU("en-ru");

    private String lang;

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
