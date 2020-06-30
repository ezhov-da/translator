package ru.ezhov.translator.core;

public class TranslateResult {
    private final String text;
    private final String additionalInformation;

    public TranslateResult(String text, String additionalInformation) {
        this.text = text;
        this.additionalInformation = additionalInformation;
    }

    public String getText() {
        return text;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }
}
