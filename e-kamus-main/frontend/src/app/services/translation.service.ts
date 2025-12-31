import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TranslationRequest {
  text: string;
  sourceLang: string;
  targetLang: string;
}

export interface TranslationResponse {
  translatedText: string;
  sourceLang: string;
  targetLang: string;
}

export interface LanguageOption {
  code: string;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private apiUrl = '/api';

  // Available languages for your translation service
  public languages: LanguageOption[] = [
    { code: 'ms', name: 'Malay' },
    { code: 'zh', name: 'Mandarin Chinese' }
  ];

  constructor(private http: HttpClient) {}

  translate(request: TranslationRequest): Observable<TranslationResponse> {
    return this.http.post<TranslationResponse>(`${this.apiUrl}/translate`, request);
  }

  // For sentence translation if your backend supports it
  translateSentence(request: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/sentence-translation`, request);
  }

  // For dictionary lookup
  lookupDictionary(word: string): Observable<any> {
    // Try the dedicated dictionary endpoint first
    return this.http.get<any>(`${this.apiUrl}/dictionary/${encodeURIComponent(word)}`);
  }
}
