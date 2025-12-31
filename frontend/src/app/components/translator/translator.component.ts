import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TranslationService, LanguageOption } from '../../services/translation.service';

@Component({
  selector: 'app-translator',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './translator.component.html',
  styleUrl: './translator.component.css'
})
export class TranslatorComponent implements OnInit {
  // Signals for reactive data
  sourceText = signal('');
  translatedText = signal('');
  sourceLang = signal('ms');
  targetLang = signal('zh');
  isLoading = signal(false);
  error = signal('');
  showDictionary = signal(false);
  dictionaryResult = signal<any>(null);
  dictionaryLookupFromMalay = signal(true); // Track if lookup was from Malay (true) or Chinese (false)

  languages: LanguageOption[] = [];

  constructor(private translationService: TranslationService) {}

  ngOnInit() {
    this.languages = this.translationService.languages;
  }

  translate() {
    const text = this.sourceText().trim();
    
    if (!text) {
      this.error.set('Please enter text to translate');
      return;
    }

    if (this.sourceLang() === this.targetLang()) {
      this.error.set('Source and target languages must be different');
      return;
    }

    this.isLoading.set(true);
    this.error.set('');
    this.translatedText.set('');

    this.translationService.translate({
      text: text,
      sourceLang: this.sourceLang(),
      targetLang: this.targetLang()
    }).subscribe({
      next: (response) => {
        this.translatedText.set(response.translatedText);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.error.set('Translation failed. Please try again.');
        console.error('Translation error:', err);
        this.isLoading.set(false);
      }
    });
  }

  swapLanguages() {
    const temp = this.sourceLang();
    this.sourceLang.set(this.targetLang());
    this.targetLang.set(temp);

    // Swap text if translation exists
    if (this.translatedText()) {
      const temp = this.sourceText();
      this.sourceText.set(this.translatedText());
      this.translatedText.set(temp);
    }
  }

  clearAll() {
    this.sourceText.set('');
    this.translatedText.set('');
    this.error.set('');
    this.dictionaryResult.set(null);
    this.showDictionary.set(false);
  }

  copyToClipboard(text: string) {
    if (!text) return;
    
    navigator.clipboard.writeText(text).then(() => {
      // Optional: Show a toast notification
      console.log('Copied to clipboard');
    });
  }

  lookupDictionary() {
    const word = this.sourceText().trim();
    
    if (!word) {
      this.error.set('Please enter a word to look up');
      return;
    }

    this.isLoading.set(true);
    this.error.set('');

    // Determine if the word is Mandarin or Malay
    const isMandarin = /[\u4E00-\u9FFF]/.test(word); // Check for Chinese characters
    
    if (isMandarin) {
      // If it's Mandarin, translate it to Malay first
      this.dictionaryLookupFromMalay.set(false); // Lookup from Chinese
      this.translationService.translate({
        text: word,
        sourceLang: 'zh',
        targetLang: 'ms'
      }).subscribe({
        next: (translationResponse) => {
          const malayWord = translationResponse.translatedText;
          // Now look up the Malay word in the dictionary
          this.translationService.lookupDictionary(malayWord).subscribe({
            next: (result) => {
              this.dictionaryResult.set(result);
              this.showDictionary.set(true);
              this.isLoading.set(false);
            },
            error: (err) => {
              this.error.set('Dictionary lookup failed');
              console.error('Dictionary error:', err);
              this.isLoading.set(false);
            }
          });
        },
        error: (err) => {
          this.error.set('Translation failed. Could not look up word.');
          console.error('Translation error:', err);
          this.isLoading.set(false);
        }
      });
    } else {
      // If it's Malay, look it up directly
      this.dictionaryLookupFromMalay.set(true); // Lookup from Malay
      this.translationService.lookupDictionary(word).subscribe({
        next: (result) => {
          this.dictionaryResult.set(result);
          this.showDictionary.set(true);
          this.isLoading.set(false);
        },
        error: (err) => {
          this.error.set('Dictionary lookup failed');
          console.error('Dictionary error:', err);
          this.isLoading.set(false);
        }
      });
    }
  }

  swapDictionaryLookup() {
    // Simply toggle the lookup direction - the template will reorder the display
    this.dictionaryLookupFromMalay.set(!this.dictionaryLookupFromMalay());

    // Also swap the source and target languages for consistency
    const temp = this.sourceLang();
    this.sourceLang.set(this.targetLang());
    this.targetLang.set(temp);

    // Update the source text to the appropriate word
    const currentResult = this.dictionaryResult();
    if (currentResult) {
      if (this.dictionaryLookupFromMalay()) {
        // Now looking from Malay, so set Malay word as source
        this.sourceText.set(currentResult.malayWord || '');
      } else {
        // Now looking from Chinese, so set Chinese word as source
        this.sourceText.set(currentResult.mandarinWord || '');
      }
    }
  }

  closeDictionary() {
    this.showDictionary.set(false);
    this.dictionaryResult.set(null);
  }

  parseExamples(examplesString: string): Array<{ chinese: string; malay: string }> {
    if (!examplesString) return [];
    
    const examples: Array<{ chinese: string; malay: string }> = [];
    
    // Split by newline first to check for Chinese/Malay pairs
    const lines = examplesString.split('\n').filter(line => line.trim());
    
    // If we have pairs of lines (every 2 lines = 1 example), treat as Chinese/Malay pairs
    if (lines.length % 2 === 0 && lines.length >= 2) {
      for (let i = 0; i < lines.length; i += 2) {
        // Remove any leading numbers from the text
        const chinesePart = lines[i].trim().replace(/^\d+\.\s*/, '');
        const malayPart = lines[i + 1].trim().replace(/^\d+\.\s*/, '');
        
        examples.push({
          chinese: chinesePart,
          malay: malayPart
        });
      }
      return examples;
    }
    
    // Try splitting by pipe separator (| format)
    if (examplesString.includes(' | ')) {
      const parts = examplesString.split(' | ').filter(part => part.trim());
      for (const part of parts) {
        examples.push({
          chinese: '',
          malay: part.trim()
        });
      }
      return examples;
    }
    
    // Otherwise, split by numbered format (1. 2. 3. etc)
    const parts = examplesString.split(/\d+\.\s+/).filter(part => part.trim());
    
    // Group by pairs (Chinese, Malay)
    for (let i = 0; i < parts.length; i += 2) {
      if (i + 1 < parts.length) {
        examples.push({
          chinese: parts[i].trim(),
          malay: parts[i + 1].trim()
        });
      }
    }
    
    return examples;
  }
}
