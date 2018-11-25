package ru.ezhov.translator.engine;

import ru.ezhov.translator.core.Translate;
import ru.ezhov.translator.core.TranslateLang;
import ru.ezhov.translator.core.TranslateResult;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class OxfordTranslate implements Translate {
    @Override
    public TranslateResult translate(TranslateLang translateLang, String word) throws Exception {
        return null;
    }

    public String dictionary(String word) {
        String ret = "404";
        try {
            final String result = getRequest(buildURL(word));
            ret = result;
        } catch (Exception e) {
            System.out.println(e);
        }
        return ret;
    }

    private String buildURL(final String word) {
        final String language = "en";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }

    private String getRequest(String link) throws Exception {
        final String app_id = "59e391ff";
        final String app_key = "3180490a4840318a230763945bea0a34";
        try {
            URL url = new URL(link);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", app_id);
            urlConnection.setRequestProperty("app_key", app_key);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
