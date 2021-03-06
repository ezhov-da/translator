package ru.ezhov.translator.gui.translate;

public class TranslateResult {
    private String text;
    private String additionalInformation;

    public TranslateResult(String text, String additionalInformation) {
        this.text = text;
        this.additionalInformation = additionalInformation;
    }

    public String getText() {
        return text;
    }

    public String getInfo() {
        return additionalInformation;
    }
}
