package ru.ezhov.translator.gui;

import ru.ezhov.translator.gui.translate.RemoteTranslate;
import ru.ezhov.translator.gui.util.version.Version;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Version("0.3")
public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    static {
        try {
            LogManager.getLogManager().readConfiguration(App.class.getResourceAsStream("/logger.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Входной параметр URL переводчика");
        } else {
            try {
                GuiApplication guiApplication = new GuiApplication(new RemoteTranslate(args[0]));
                guiApplication.run();
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Ошибка при запуске приложения", e);
            }
        }
    }
}
