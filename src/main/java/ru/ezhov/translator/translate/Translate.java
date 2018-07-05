package ru.ezhov.translator.translate;

import com.google.gson.Gson;
import ru.ezhov.translator.util.HttpsUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Translate {
    private static final Logger LOG = Logger.getLogger(Translate.class.getName());

    private String key;

    public Translate(String key) {
        this.key = key;
    }

    public String translate(TranslateLang translateLang, String word) throws Exception {
        HttpsUtil.enableAllTrustingSsl();
        String buildUrl = buildUrl(translateLang, word);
        System.out.println(buildUrl);
        URL url = new URL(buildUrl);
        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
        int code = https.getResponseCode();
        switch (code) {
            case HttpURLConnection.HTTP_OK:
                try (Scanner scanner = new Scanner(https.getInputStream(), "UTF-8")) {
                    if (scanner.hasNextLine()) {
                        String text = scanner.nextLine();
                        Gson gson = new Gson();
                        AnswerOk answerOk = gson.fromJson(text, AnswerOk.class);
                        return answerOk.getText().get(0);
                    } else {
                        return "";
                    }
                }
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
                        return answerOk.getMessage();
                    } else {
                        return "Error";
                    }
                }
            default:
                return "Error";
        }
    }

    private String buildUrl(TranslateLang translateLang, String word) throws UnsupportedEncodingException {
        String wordEncode = URLEncoder.encode(word, "UTF-8");
        return String.format(
                "https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&text=%s&lang=%s",
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
