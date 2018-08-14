package ru.ezhov.translator.engine;

import org.junit.Ignore;
import org.junit.Test;
import ru.ezhov.translator.core.TranslateLang;

import static org.junit.Assert.assertEquals;

@Ignore
public class YandexTranslateTest {
    @Test
    public void translate() throws Exception {
        YandexTranslate yandexTranslate = new YandexTranslate();
        String text = yandexTranslate.translate(TranslateLang.EN_RU, "test");
        assertEquals("тест", text);
    }

}