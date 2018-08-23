package ru.ezhov.translator.web.service;

import ru.ezhov.translator.core.Translate;
import ru.ezhov.translator.engine.MultitranTranslate;
import ru.ezhov.translator.engine.YandexTranslate;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EngineFactory {

    public Translate getTranslate(Engine engine) {
        switch (engine) {
            case YANDEX:
                return new YandexTranslate();
            case MULTITRAN:
                return new MultitranTranslate();
            default:
                throw new IllegalArgumentException("Неподдерживаемый движок перевода");
        }
    }

}
