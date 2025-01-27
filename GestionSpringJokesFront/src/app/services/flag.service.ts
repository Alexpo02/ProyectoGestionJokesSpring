import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Flag } from '../models/flag.model';

@Injectable({
  providedIn: 'root',
})
export class FlagService {
  private baseUrl = 'http://localhost:8080/api/flags';

  constructor(private http: HttpClient) {}

  getFlags(): Observable<Flag[]> {
    return this.http.get<Flag[]>(this.baseUrl);
  }

  getFlag(id: number): Observable<Flag> {
    return this.http.get<Flag>(`${this.baseUrl}/${id}`);
  }

  createFlag(flag: Flag): Observable<Flag> {
    return this.http.post<Flag>(this.baseUrl, flag);
  }

  updateFlag(id: number, flag: Flag): Observable<Flag> {
    return this.http.put<Flag>(`${this.baseUrl}/${id}`, flag);
  }

  deleteFlag(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
