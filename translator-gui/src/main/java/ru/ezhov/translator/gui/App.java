package ru.ezhov.translator.gui;

import ru.ezhov.translator.core.Translate;
import ru.ezhov.translator.gui.util.version.Version;

import java.util.logging.Level;
import java.util.logging.Logger;

@Version("0.1")
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
                GuiApplication guiApplication = new GuiApplication(translate);
                guiApplication.run();
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Ошибка при запуске приложения", e);
            }
        }
    }
}
