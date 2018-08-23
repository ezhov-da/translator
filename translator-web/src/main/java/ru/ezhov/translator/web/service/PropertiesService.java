package ru.ezhov.translator.web.service;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

@ApplicationScoped
public class PropertiesService {
    private static final Logger LOG = Logger.getLogger(PropertiesService.class.getName());

    public PropertiesService() throws Exception {
        Properties properties = new Properties();
        properties.load(PropertiesService.class.getResourceAsStream("/server.properties"));
        LOG.info("Загрузка настроек приложения");
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            System.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
        LOG.info("Настройки загружены");
    }
}
