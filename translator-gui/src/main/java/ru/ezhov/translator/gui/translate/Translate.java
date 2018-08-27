package ru.ezhov.translator.gui.translate;


public interface Translate {
    TranslateResult translate(Engine engine, TranslateLang translateLang, String word) throws Exception;
}
