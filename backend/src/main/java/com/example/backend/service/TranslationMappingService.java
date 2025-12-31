package com.example.backend.service;

import org.springframework.stereotype.Service;
import java.util.*;

/**
 * TranslationMappingService provides curated translations for common Malay-Mandarin word pairs.
 * This ensures better translation quality for frequently used words.
 */
@Service
public class TranslationMappingService {
    
    // Malay to Mandarin translations
    private static final Map<String, String> MALAY_TO_MANDARIN = new HashMap<>();
    
    // Mandarin to Malay translations
    private static final Map<String, String> MANDARIN_TO_MALAY = new HashMap<>();
    
    // Sentence/phrase translations
    private static final Map<String, String> MANDARIN_TO_MALAY_SENTENCES = new HashMap<>();
    private static final Map<String, String> MALAY_TO_MANDARIN_SENTENCES = new HashMap<>();
    
    static {
        initializeTranslations();
    }
    
    private static void initializeTranslations() {
        // Emotions
        MALAY_TO_MANDARIN.put("gembira", "开心");
        MALAY_TO_MANDARIN.put("sedih", "伤心");
        MALAY_TO_MANDARIN.put("marah", "生气");
        MALAY_TO_MANDARIN.put("takut", "害怕");
        MALAY_TO_MANDARIN.put("cinta", "爱");
        MALAY_TO_MANDARIN.put("rindu", "想念");
        
        // Adjectives
        MALAY_TO_MANDARIN.put("cantik", "美丽");
        MALAY_TO_MANDARIN.put("handsome", "英俊");
        MALAY_TO_MANDARIN.put("besar", "大");
        MALAY_TO_MANDARIN.put("kecil", "小");
        MALAY_TO_MANDARIN.put("panas", "热");
        MALAY_TO_MANDARIN.put("dingin", "冷");
        MALAY_TO_MANDARIN.put("baru", "新");
        MALAY_TO_MANDARIN.put("tua", "旧");
        MALAY_TO_MANDARIN.put("baik", "好");
        MALAY_TO_MANDARIN.put("jahat", "坏");
        MALAY_TO_MANDARIN.put("bulat", "圆形");
        MALAY_TO_MANDARIN.put("bujur", "椭圆形");
        MALAY_TO_MANDARIN.put("buncit", "肚子大");
        MALAY_TO_MANDARIN.put("busuk", "臭");
        MALAY_TO_MANDARIN.put("cair", "融化");
        MALAY_TO_MANDARIN.put("berani", "勇敢");
        MALAY_TO_MANDARIN.put("zalim", "残忍");
        MALAY_TO_MANDARIN.put("kejam", "残酷");
        MALAY_TO_MANDARIN.put("sedap", "美味");
        
        // Common verbs
        MALAY_TO_MANDARIN.put("makan", "吃");
        MALAY_TO_MANDARIN.put("minum", "喝");
        MALAY_TO_MANDARIN.put("tidur", "睡");
        MALAY_TO_MANDARIN.put("berjalan", "走");
        MALAY_TO_MANDARIN.put("berlari", "跑");
        MALAY_TO_MANDARIN.put("membaca", "读");
        MALAY_TO_MANDARIN.put("menulis", "写");
        MALAY_TO_MANDARIN.put("berbicara", "说");
        MALAY_TO_MANDARIN.put("mendengarkan", "听");
        MALAY_TO_MANDARIN.put("bekerja", "工作");
        MALAY_TO_MANDARIN.put("belajar", "学");
        MALAY_TO_MANDARIN.put("bermain", "玩");
        
        // Nature
        MALAY_TO_MANDARIN.put("air", "水");
        MALAY_TO_MANDARIN.put("api", "火");
        MALAY_TO_MANDARIN.put("batu", "石");
        MALAY_TO_MANDARIN.put("pohon", "树");
        MALAY_TO_MANDARIN.put("bunga", "花");
        MALAY_TO_MANDARIN.put("gunung", "山");
        MALAY_TO_MANDARIN.put("sungai", "河");
        MALAY_TO_MANDARIN.put("laut", "海");
        
        // Common compliments and phrases (Chinese to Malay)
        MANDARIN_TO_MALAY_SENTENCES.put("你太聪明了，这道题都被你解出来了。", "Kamu sangat bijak, soalan ini pun kamu boleh selesaikan.");
        MANDARIN_TO_MALAY_SENTENCES.put("你很聪明", "Kamu sangat bijak");
        MANDARIN_TO_MALAY_SENTENCES.put("你真棒", "Kamu sangat hebat");
        MANDARIN_TO_MALAY_SENTENCES.put("很好的想法", "Idea yang sangat bagus");
        
        // Common compliments and phrases (Malay to Chinese)
        MALAY_TO_MANDARIN_SENTENCES.put("Kamu sangat bijak, soalan ini pun kamu boleh selesaikan.", "你太聪明了，这道题都被你解出来了。");
        MALAY_TO_MANDARIN_SENTENCES.put("Kamu sangat bijak", "你很聪明");
        MALAY_TO_MANDARIN_SENTENCES.put("Kamu sangat hebat", "你真棒");
        MALAY_TO_MANDARIN_SENTENCES.put("Idea yang sangat bagus", "很好的想法");
        
        // Create reverse mapping
        for (Map.Entry<String, String> entry : MALAY_TO_MANDARIN.entrySet()) {
            MANDARIN_TO_MALAY.put(entry.getValue(), entry.getKey());
        }
    }
    
    /**
     * Get translation from Malay to Mandarin
     */
    public String getMalayToMandarin(String malayWord) {
        if (malayWord == null) return null;
        return MALAY_TO_MANDARIN.get(malayWord.toLowerCase());
    }
    
    /**
     * Get translation from Mandarin to Malay
     */
    public String getMalayFromMandarin(String mandarinWord) {
        if (mandarinWord == null) return null;
        return MANDARIN_TO_MALAY.get(mandarinWord);
    }
    
    /**
     * Check if a Malay word has a mapping
     */
    public boolean hasMalayTranslation(String malayWord) {
        return malayWord != null && MALAY_TO_MANDARIN.containsKey(malayWord.toLowerCase());
    }
    
    /**
     * Check if a Mandarin word has a mapping
     */
    public boolean hasMandarinTranslation(String mandarinWord) {
        return mandarinWord != null && MANDARIN_TO_MALAY.containsKey(mandarinWord);
    }
    
    /**
     * Get all available Malay words
     */
    public Set<String> getAllMalayWords() {
        return new HashSet<>(MALAY_TO_MANDARIN.keySet());
    }
    
    /**
     * Get all available Mandarin words
     */
    public Set<String> getAllMandarinWords() {
        return new HashSet<>(MANDARIN_TO_MALAY.keySet());
    }
    
    /**
     * Get sentence translation from Mandarin to Malay
     */
    public String getMandarinSentenceToMalay(String mandarinSentence) {
        if (mandarinSentence == null) return null;
        return MANDARIN_TO_MALAY_SENTENCES.get(mandarinSentence);
    }
    
    /**
     * Get sentence translation from Malay to Mandarin
     */
    public String getMalaySentenceToMandarin(String malaySentence) {
        if (malaySentence == null) return null;
        return MALAY_TO_MANDARIN_SENTENCES.get(malaySentence);
    }
    
    /**
     * Check if a Mandarin sentence has a mapping
     */
    public boolean hasMandarinSentenceMapping(String mandarinSentence) {
        return mandarinSentence != null && MANDARIN_TO_MALAY_SENTENCES.containsKey(mandarinSentence);
    }
    
    /**
     * Check if a Malay sentence has a mapping
     */
    public boolean hasMalaySentenceMapping(String malaySentence) {
        return malaySentence != null && MALAY_TO_MANDARIN_SENTENCES.containsKey(malaySentence);
    }
}
