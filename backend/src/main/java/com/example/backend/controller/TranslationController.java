package com.example.backend.controller;

import com.example.backend.model.TranslationRequest;
import com.example.backend.model.TranslationResponse;
import com.example.backend.service.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    /**
     * POST endpoint for translating text between languages
     * Expected request body: { "text": "hello", "sourceLang": "en", "targetLang": "ms" }
     */
    @PostMapping("/translate")
    public Mono<ResponseEntity<TranslationResponse>> translate(
            @RequestBody TranslationRequest request) {
        
        // Validate input
        if (request.getText() == null || request.getText().trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        String sourceLanguage = request.getSourceLang() != null ? request.getSourceLang() : "en";
        String targetLanguage = request.getTargetLang() != null ? request.getTargetLang() : "ms";

        // Call translation service
        return translationService.translateText(request.getText(), sourceLanguage, targetLanguage)
                .map(translatedText -> {
                    TranslationResponse response = new TranslationResponse();
                    response.setTranslatedText(translatedText);
                    response.setSourceLang(sourceLanguage);
                    response.setTargetLang(targetLanguage);
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(e -> {
                    // Return error response
                    TranslationResponse errorResponse = new TranslationResponse();
                    errorResponse.setTranslatedText("Translation error: " + e.getMessage());
                    errorResponse.setSourceLang(sourceLanguage);
                    errorResponse.setTargetLang(targetLanguage);
                    return Mono.just(ResponseEntity.ok(errorResponse));
                });
    }
}
