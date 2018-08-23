package ru.ezhov.translator.engine;

import com.google.gson.Gson;
import ru.ezhov.translator.core.Translate;
import ru.ezhov.translator.core.TranslateLang;
import ru.ezhov.translator.core.TranslateResult;
import ru.ezhov.translator.core.util.HttpsUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YandexTranslate implements Translate {
    private static final Logger LOG = Logger.getLogger(YandexTranslate.class.getName());

    private String url;
    private String key;

    public YandexTranslate() {
        this.key = System.getProperty("yandex.key");
        this.url = System.getProperty("yandex.url");
        if (this.key == null || this.url == null) {
            LOG.log(Level.SEVERE,
                    "Не указаны необходимые ключи при запуске приложения. \n" +
                            "Для работы Яндекс переводчика необходимо указать параметры:\n {0}\n{1}\n" +
                            "Более подробно Вы можете ознакомиться на https://translate.yandex.ru/developers/keys",
                    new Object[]{
                            "-Dyandex.key - ключ для Яндекс API переводчика", "-Dyandex.url - ссылка на API переводчика"
                    }
            );
        } else {
            LOG.log(Level.INFO, "Ключ: {0}", key);
            LOG.log(Level.INFO, "Ссылка: {0}", url);
        }
    }

    @Override
    public TranslateResult translate(TranslateLang translateLang, String word) throws Exception {
        final String link = "Переведено сервисом «Яндекс.Переводчик» http://translate.yandex.ru/";
        HttpsUtil.enableAllTrustingSsl();
        String buildUrl = buildUrl(translateLang, word);
        LOG.log(Level.INFO, "Построенная ссылка: {0}", buildUrl);
        URL url = new URL(buildUrl);
        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
        int code = https.getResponseCode();
        String resultText;
        switch (code) {
            case HttpURLConnection.HTTP_OK:
                try (Scanner scanner = new Scanner(https.getInputStream(), "UTF-8")) {
                    if (scanner.hasNextLine()) {
                        String text = scanner.nextLine();
                        Gson gson = new Gson();
                        AnswerOk answerOk = gson.fromJson(text, AnswerOk.class);
                        resultText = answerOk.getText().get(0);
                    } else {
                        resultText = "";
                    }
                }
                break;
            case 401:
            case 402:
            case 404:
            case 413:
            case 422:
            case 501:
                try (Scanner scanner = new Scanner(https.getInputStream())) {
                    if (scanner.hasNextLine()) {
                        String text = scanner.nextLine();
                        Gson gson = new Gson();
                        AnswerError answerOk = gson.fromJson(text, AnswerError.class);
                        resultText = answerOk.getMessage();
                    } else {
                        resultText = "Error";
                    }
                }
                break;
            default:
                resultText = "Error";
        }
        return new TranslateResult(resultText, link);
    }

    private String buildUrl(TranslateLang translateLang, String word) throws UnsupportedEncodingException {
        String wordEncode = URLEncoder.encode(word, "UTF-8");
        return String.format(
                url,
                key,
                wordEncode,
                translateLang.getLang());
    }

    private class AnswerOk {
        private String code;
        private String lang;
        private List<String> text;

        public String getCode() {
            return code;
        }

        public String getLang() {
            return lang;
        }

        public List<String> getText() {
            return text;
        }
    }

    private class AnswerError {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
