import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Language } from '../models/language.model';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private baseUrl = 'http://localhost:8080/api/languages';

  constructor(private http: HttpClient) {}

  getLanguages(): Observable<Language[]> {
    return this.http.get<Language[]>(this.baseUrl);
  }

  getLanguage(id: number): Observable<Language> {
    return this.http.get<Language>(`${this.baseUrl}/${id}`);
  }

  createLanguage(language: Language): Observable<Language> {
    return this.http.post<Language>(this.baseUrl, language);
  }

  updateLanguage(id: number, language: Language): Observable<Language> {
    return this.http.put<Language>(`${this.baseUrl}/${id}`, language);
  }

  deleteLanguage(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
