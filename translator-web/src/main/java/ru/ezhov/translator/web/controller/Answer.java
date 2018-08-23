package ru.ezhov.translator.web.controller;

public class Answer {
    private String text;
    private String info;

    public Answer(String text, String info) {
        this.text = text;
        this.info = info;
    }

    public String getText() {
        return text;
    }

    public String getInfo() {
        return info;
    }
}
