package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LibreTranslateRequest {
    @JsonProperty("q")
    private String text;
    
    @JsonProperty("source")
    private String source;
    
    @JsonProperty("target")
    private String target;
    
    @JsonProperty("format")
    private String format;
    
    @JsonProperty("api_key")
    private String apiKey;

    public LibreTranslateRequest() {
    }

    public LibreTranslateRequest(String text, String source, String target) {
        this.text = text;
        this.source = source;
        this.target = target;
        this.format = "text";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
