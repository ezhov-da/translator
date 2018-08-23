package ru.ezhov.translator.core;

public interface Translate {
    TranslateResult translate(TranslateLang translateLang, String word) throws Exception;
}
