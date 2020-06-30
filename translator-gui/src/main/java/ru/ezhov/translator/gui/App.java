package ru.ezhov.translator.gui;

import ru.ezhov.translator.gui.translate.RemoteTranslate;
import ru.ezhov.translator.gui.util.HttpsUtil;
import ru.ezhov.translator.gui.util.version.Version;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Version("0.0.4")
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
        try {
            HttpsUtil.enableAllTrustingSsl();

            String defaultUrl = "https://prog-tools.ru:8445/translator/rest/translate/v1?engine=%s&lang=%s&text=%s";
            String url = System.getProperty("translator.url", defaultUrl);

            GuiApplication guiApplication = new GuiApplication(new RemoteTranslate(url));
            guiApplication.run();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Ошибка при запуске приложения", e);
        }
    }
}
