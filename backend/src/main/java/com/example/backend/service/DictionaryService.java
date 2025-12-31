package com.example.backend.service;

import com.example.backend.model.DictionaryResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DictionaryService {

    private final TranslationService translationService;
    private final DeepseekAiService deepseekAiService;
    private final DictionaryEntryService dictionaryEntryService;

    public DictionaryService(TranslationService translationService,
            DeepseekAiService deepseekAiService,
            DictionaryEntryService dictionaryEntryService) {
        this.translationService = translationService;
        this.deepseekAiService = deepseekAiService;
        this.dictionaryEntryService = dictionaryEntryService;
    }

    public Mono<DictionaryResponse> processWord(String malayWord) {
        System.out.println("Processing word: " + malayWord);

        // First check if we have a complete curated entry
        if (dictionaryEntryService.hasEntry(malayWord)) {
            System.out.println("Found complete curated entry for: " + malayWord);
            DictionaryEntryService.DictionaryEntry curatedEntry = dictionaryEntryService.getEntryByMalayWord(malayWord);
            DictionaryResponse response = dictionaryEntryService.convertToDictionaryResponse(curatedEntry, true);
            System.out.println("Returning complete curated response for: " + malayWord);
            return Mono.just(response);
        }

        // Check for pronunciation and adjective overrides (for AI-generated content)
        final String pronunciationOverride = dictionaryEntryService.getPronunciationOverride(malayWord);
        final Boolean adjectiveOverride = dictionaryEntryService.getAdjectiveOverride(malayWord);

        System.out.println("Pronunciation override: " + pronunciationOverride);
        System.out.println("Adjective override: " + adjectiveOverride);

        // Detect if input is Chinese (Mandarin) using regex for Chinese characters
        boolean isChinese = malayWord.matches(".*[\\u4E00-\\u9FFF].*");
        System.out.println("Is Chinese: " + isChinese);

        Mono<DictionaryResponse> result;

        if (isChinese) {
            // If it's already Chinese, use it directly as the Mandarin word
            System.out.println("Input is Chinese, using directly for explanation: " + malayWord);
            result = deepseekAiService.generateExplanation(malayWord, "Mandarin")
                    .doOnNext(aiResponse -> {
                        System.out.println("DeepseekAi response received:");
                        System.out.println("- Explanation: " + aiResponse.getExplanation());
                        System.out.println("- Examples: " + aiResponse.getExamples());
                        System.out.println("- Pronunciation: " + aiResponse.getPronunciation());
                        System.out.println("- Is Adjective: " + aiResponse.isAdjective());
                    })
                    .map(aiResponse -> {
                        DictionaryResponse response = new DictionaryResponse();
                        response.setMalayWord("(meaning)");
                        response.setMandarinWord(malayWord);
                        response.setExplanation(aiResponse.getExplanation());
                        response.setExamples(aiResponse.getExamples());

                        // Use pronunciation override if available, otherwise use AI
                        if (pronunciationOverride != null) {
                            response.setPinyin(pronunciationOverride);
                            System.out.println("Using pronunciation override: " + pronunciationOverride);
                        } else {
                            String pronunciation = aiResponse.getPronunciation();
                            if (pronunciation == null || pronunciation.isEmpty()) {
                                response.setPinyin("No pronunciation available");
                            } else {
                                response.setPinyin(pronunciation);
                            }
                        }

                        // Use adjective override if available, otherwise use AI
                        if (adjectiveOverride != null) {
                            response.setAdjective(adjectiveOverride);
                            System.out.println("Using adjective override: " + adjectiveOverride);
                        } else {
                            response.setAdjective(aiResponse.isAdjective());
                        }

                        return response;
                    });
        } else {
            // If it's Malay, translate to Chinese first
            System.out.println("Input is Malay, translating: " + malayWord);
            result = translationService.translateText(malayWord, "ms", "zh")
                    .flatMap(mandarinWord -> {
                        System.out.println("Translation successful: '" + malayWord + "' → '" + mandarinWord + "'");
                        System.out.println("Calling DeepseekAiService for '" + mandarinWord + "'");

                        // Now use DeepseekAi to get detailed information about the word
                        return deepseekAiService.generateExplanation(mandarinWord, "Mandarin")
                                .doOnNext(aiResponse -> {
                                    System.out.println("DeepseekAi response received:");
                                    System.out.println("- Explanation: " + aiResponse.getExplanation());
                                    System.out.println("- Examples: " + aiResponse.getExamples());
                                    System.out.println("- Pronunciation: " + aiResponse.getPronunciation());
                                    System.out.println("- Is Adjective: " + aiResponse.isAdjective());
                                })
                                .map(aiResponse -> {
                                    DictionaryResponse response = new DictionaryResponse();
                                    response.setMalayWord(malayWord);
                                    response.setMandarinWord(mandarinWord);
                                    response.setExplanation(aiResponse.getExplanation());
                                    response.setExamples(aiResponse.getExamples());

                                    // Use pronunciation override if available, otherwise use AI
                                    if (pronunciationOverride != null) {
                                        response.setPinyin(pronunciationOverride);
                                        System.out.println("Using pronunciation override: " + pronunciationOverride);
                                    } else {
                                        String pronunciation = aiResponse.getPronunciation();
                                        if (pronunciation == null || pronunciation.isEmpty()) {
                                            response.setPinyin("No pronunciation available");
                                        } else {
                                            response.setPinyin(pronunciation);
                                        }
                                    }

                                    // Use adjective override if available, otherwise use AI
                                    if (adjectiveOverride != null) {
                                        response.setAdjective(adjectiveOverride);
                                        System.out.println("Using adjective override: " + adjectiveOverride);
                                    } else {
                                        response.setAdjective(aiResponse.isAdjective());
                                    }

                                    return response;
                                });
                    });
        }

        return result.onErrorResume(e -> {
            System.err.println("Error processing word: " + malayWord + ", error: " + e.getMessage());

            // Create an error response instead of throwing an exception
            DictionaryResponse errorResponse = new DictionaryResponse();
            errorResponse.setMalayWord(malayWord);
            errorResponse.setMandarinWord("Translation failed");
            errorResponse.setExplanation(
                    "Unable to translate this word. LibreTranslate API error: " + e.getMessage());
            errorResponse.setExamples("No examples available");
            errorResponse.setPinyin("No pronunciation available");
            errorResponse.setAdjective(false);

            return Mono.just(errorResponse);
        });
    }
}
