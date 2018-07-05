package ru.ezhov.translator.translate;

public enum TranslateLang {
	RU_EN("ru-en"), EN_RU("en-ru");

	private String lang;

	TranslateLang(String lang) {
		this.lang = lang;
	}

	public String getLang() {
		return lang;
	}
}
