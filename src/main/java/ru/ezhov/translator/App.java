package ru.ezhov.translator;

import ru.ezhov.translator.gui.Gui;
import ru.ezhov.translator.translate.Translate;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Входной параметр [API-ключ: https://translate.yandex.ru/developers/keys] обязателен для указания");
        } else {
            String apiKey = args[0];
            Gui gui = new Gui(new Translate(apiKey));
            gui.run();
        }
    }
}
