package ru.ezhov.translator.core;

public interface Translate {
    String translate(TranslateLang translateLang, String word) throws Exception;
}
