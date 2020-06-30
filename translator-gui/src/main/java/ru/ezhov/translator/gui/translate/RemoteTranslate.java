package ru.ezhov.translator.gui.translate;

import com.google.gson.Gson;
import org.javalite.http.Get;
import org.javalite.http.Http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteTranslate implements Translate {
    private final String url;
    private Engine engine;

    public RemoteTranslate(String url) {
        this.url = url;
    }

    private static final Logger LOG = Logger.getLogger(RemoteTranslate.class.getName());

    @Override
    public TranslateResult translate(Engine engine, TranslateLang translateLang, String word) throws Exception {
        Get get = Http.get(buildUrl(engine, translateLang, word), 60000, 60000);
        if (get.responseCode() == 200) {
            Gson gson = new Gson();
            Answer answer = gson.fromJson(get.text(), Answer.class);
            return new TranslateResult(answer.getText(), answer.getInfo());
        } else {
            LOG.log(Level.SEVERE, "Получен код ответа от сервера:  " + get.responseCode(), get.text());
            return new TranslateResult("-", get.text());
        }
    }

    private String buildUrl(Engine engine, TranslateLang translateLang, String word) throws UnsupportedEncodingException {
        return String.format(
                url,
                engine.name().toLowerCase(),
                translateLang.getLang(),
                URLEncoder.encode(word, "UTF-8")
        );
    }

    private static class Answer {
        private String text;
        private String info;

        public String getText() {
            return text;
        }

        public String getInfo() {
            return info;
        }
    }
}