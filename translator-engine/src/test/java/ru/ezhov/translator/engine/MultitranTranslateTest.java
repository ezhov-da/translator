package ru.ezhov.translator.engine;

import org.junit.Test;
import ru.ezhov.translator.core.Translate;
import ru.ezhov.translator.core.TranslateLang;

import static org.junit.Assert.assertNotEquals;

public class MultitranTranslateTest {
    @Test
    public void translateRuEnOk() throws Exception {
        System.setProperty("multitran.url", "https://www.multitran.ru/c/m.exe?l1=1&l2=2&s=%s");

        Translate translate = new MultitranTranslate();

        assertNotEquals("", translate.translate(TranslateLang.RU_EN, "тест"));
    }

    @Test
    public void translateEnRuOk() throws Exception {
        System.setProperty("multitran.url", "https://www.multitran.ru/c/m.exe?l1=1&l2=2&s=%s");

        Translate translate = new MultitranTranslate();

        assertNotEquals("", translate.translate(TranslateLang.RU_EN, "evaluate"));
    }
}