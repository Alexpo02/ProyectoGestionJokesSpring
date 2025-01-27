import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Joke } from '../models/joke.model';

@Injectable({
  providedIn: 'root',
})
export class JokeService {
  private baseUrl = 'http://localhost:8080/api/jokes';

  constructor(private http: HttpClient) {}

  getJokes(): Observable<Joke[]> {
    return this.http.get<Joke[]>(this.baseUrl);
  }

  getJoke(id: number): Observable<Joke> {
    return this.http.get<Joke>(`${this.baseUrl}/${id}`);
  }

  createJoke(joke: Joke): Observable<Joke> {
    return this.http.post<Joke>(this.baseUrl, joke);
  }

  updateJoke(id: number, joke: Joke): Observable<Joke> {
    return this.http.put<Joke>(`${this.baseUrl}/${id}`, joke);
  }

  deleteJoke(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
