package ru.ezhov.translator.engine;

import org.junit.Test;

public class OxfordTranslateTest {
    @Test
    public void dictionary() throws Exception {
        OxfordTranslate oxfordTranslate = new OxfordTranslate();
        System.out.println(oxfordTranslate.dictionary("hello"));
    }

}