package ru.ezhov.translator.engine;

import org.javalite.http.Get;
import org.javalite.http.Http;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.ezhov.translator.core.Translate;
import ru.ezhov.translator.core.TranslateLang;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultitranTranslate implements Translate {
    private static final Logger LOG = Logger.getLogger(MultitranTranslate.class.getName());
    private String url;

    public MultitranTranslate() {
        url = System.getProperty("multitran.url");
        if (url == null) {
            LOG.log(Level.WARNING, "Не указан ключ: multitran.url");
        } else {
            LOG.log(Level.INFO, "Kлюч: multitran.url: {0} ", url);
        }
    }

    @Override
    public String translate(TranslateLang translateLang, String word) throws Exception {
        Get get = Http.get(buildUrl(word));
        String text = get.text("cp1251");
        Document document = Jsoup.parse(text);
        Elements elements = document.getElementsByTag("table");
        Element element = elements.get(9);
        Elements trs = element.getElementsByTag("tr");
        for (int trCounter = 1; trCounter < trs.size(); trCounter++) {
            Element tr = trs.get(trCounter);
            Elements tds = tr.getElementsByTag("td");
            if (tds.size() > 1) {
                for (int tdCounter = 0; tdCounter < tds.size(); tdCounter += 2/*всегда по 2*/) {
                    Element tdDescription = tds.get(tdCounter);
                    LOG.log(Level.FINEST, "Описание: {0}", tdDescription);
                    String description = getDescription(tdDescription);
                    Element tdTranslate = tds.get(tdCounter + 1);
                    String translate = getTranslate(tdTranslate);
                    LOG.log(Level.FINEST, "Перевод: {0}", tdTranslate);
                    if ("Общая лексика".equals(description)) {
                        return translate;
                    }
                }
            }
        }
        return "Ошибка работы";
    }

    private String buildUrl(String text) throws UnsupportedEncodingException {
        return String.format(url, URLEncoder.encode(text, "utf-8"));
    }

    private String getDescription(Element element) {
        Elements as = element.getElementsByTag("a");
        Element a = as.get(0);
        return a.attr("title");
    }

    private String getTranslate(Element element) {
        Elements as = element.getElementsByTag("a");
        StringBuilder stringBuilder = new StringBuilder();
        for (Element a : as) {
            stringBuilder
                    .append(a.text())
                    .append(", ");
        }
        String translate = stringBuilder.toString();
        return translate.substring(0, translate.length() - 2);
    }
}
