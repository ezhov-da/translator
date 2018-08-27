package ru.ezhov.translator.gui;

import ru.ezhov.translator.gui.translate.Engine;
import ru.ezhov.translator.gui.translate.Translate;
import ru.ezhov.translator.gui.translate.TranslateLang;
import ru.ezhov.translator.gui.translate.TranslateResult;

import javax.swing.*;

public class TranslateWorker extends SwingWorker<TranslateResult, Object> {
    private OutputResult outputResultTranslate;
    private OutputResult outputResultInfo;
    private Translate translate;
    private Engine engine;
    private TranslateLang translateLang;
    private String text;

    public TranslateWorker(
            OutputResult outputResultTranslate,
            OutputResult outputResultInfo,
            Translate translate,
            Engine engine,
            TranslateLang translateLang,
            String text
    ) {
        this.outputResultTranslate = outputResultTranslate;
        this.outputResultInfo = outputResultInfo;
        this.translate = translate;
        this.engine = engine;
        this.translateLang = translateLang;
        this.text = text;
    }

    @Override
    protected TranslateResult doInBackground() throws Exception {
        return translate.translate(engine, translateLang, text);
    }

    @Override
    protected void done() {
        try {
            TranslateResult translateResult = get();
            outputResultTranslate.setText(translateResult.getText());
            outputResultInfo.setText(translateResult.getInfo());
        } catch (Exception e) {
            outputResultInfo.setText(e.getMessage());
            e.printStackTrace();
        }
    }
}
