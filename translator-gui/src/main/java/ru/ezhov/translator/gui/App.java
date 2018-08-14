package ru.ezhov.translator.gui;

import ru.ezhov.translator.core.Translate;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Входной параметр реализация класса переводчика");
        } else {
            String clazzNameTranslate = args[0];
            try {
                Class translateClass = Class.forName(clazzNameTranslate);
                Translate translate = (Translate) translateClass.newInstance();
                Gui gui = new Gui(translate);
                gui.run();
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Ошибка при запуске приложения", e);
            }
        }
    }
}
