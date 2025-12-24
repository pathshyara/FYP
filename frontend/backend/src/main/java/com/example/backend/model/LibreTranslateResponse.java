package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LibreTranslateResponse {
    @JsonProperty("translatedText")
    private String translatedText;

    public LibreTranslateResponse() {
    }

    public LibreTranslateResponse(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
