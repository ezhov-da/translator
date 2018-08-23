package ru.ezhov.translator.gui.translate;

import com.google.gson.Gson;
import org.javalite.http.Get;
import org.javalite.http.Http;
import ru.ezhov.translator.core.TranslateLang;
import ru.ezhov.translator.core.TranslateResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RestTranslate {
    public enum Engine {YANDEX, MULTITRAN}

    private String url;
    private Engine engine;

    public RestTranslate(String url) {
        this.url = url;
    }

    public TranslateResult translate(Engine engine, TranslateLang translateLang, String word) throws Exception {
        Get get = Http.get(buildUrl(engine, translateLang, word));
        if (get.responseCode() == 200) {
            Gson gson = new Gson();
            Answer answer = gson.fromJson(get.text(), Answer.class);
            return new TranslateResult(answer.getText(), answer.getInfo());
        } else {
            return new TranslateResult("Ошибка", "Не удалось получить ответ от сервера");
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

    private class Answer {
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
