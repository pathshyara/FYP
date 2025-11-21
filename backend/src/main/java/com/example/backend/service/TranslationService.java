package com.example.backend.service;

import com.example.backend.model.LibreTranslateRequest;
import com.example.backend.model.LibreTranslateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class TranslationService {

    private final WebClient webClient;

    @Value("${libretranslate.api.url}")
    private String libreTranslateApiUrl;

    @Value("${libretranslate.api.key:#{null}}")
    private String libreTranslateApiKey;

    public TranslationService(WebClient webClient) {
        this.webClient = webClient;
        System.out.println("TranslationService initialized with WebClient: " + webClient);
    }

    public Mono<String> translateText(String text, String sourceLanguage, String targetLanguage) {
        // Create LibreTranslate request with proper field names
        LibreTranslateRequest request = new LibreTranslateRequest(text, sourceLanguage, targetLanguage);

        // Add API key if configured
        if (libreTranslateApiKey != null && !libreTranslateApiKey.isEmpty()
                && !libreTranslateApiKey.equals("your_libretranslate_api_key")) {
            request.setApiKey(libreTranslateApiKey);
        }

        System.out.println("Calling LibreTranslate API at: " + libreTranslateApiUrl);
        System.out.println("Translating: '" + text + "' from " + sourceLanguage + " to " + targetLanguage);

        // Call the LibreTranslate API
        return webClient.post()
                .uri(libreTranslateApiUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LibreTranslateResponse.class)
                .doOnNext(response -> {
                    System.out.println("Received LibreTranslate API response: " + response.getTranslatedText());
                })
                .map(response -> {
                    String translated = response.getTranslatedText();
                    System.out.println("Translation successful: '" + text + "' → '" + translated + "'");
                    return translated;
                })
                .doOnError(error -> {
                    System.err.println("Error during LibreTranslate API call to " + libreTranslateApiUrl);
                    System.err.println("Error message: " + error.getMessage());
                    
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException webError = (WebClientResponseException) error;
                        System.err.println("Status: " + webError.getStatusCode());
                        System.err.println("Response body: " + webError.getResponseBodyAsString());
                    }
                    
                    if (error.getCause() != null) {
                        System.err.println("Caused by: " + error.getCause().getMessage());
                    }
                    error.printStackTrace();
                })
                .onErrorResume(e -> {
                    String errorMsg = e.getMessage();
                    if (e instanceof WebClientResponseException) {
                        WebClientResponseException webError = (WebClientResponseException) e;
                        errorMsg = "LibreTranslate API error (" + webError.getStatusCode() + "): " 
                                + webError.getResponseBodyAsString();
                    }
                    System.err.println("Translation failed: " + errorMsg);
                    return Mono.error(new RuntimeException(errorMsg));
                });
    }
}

