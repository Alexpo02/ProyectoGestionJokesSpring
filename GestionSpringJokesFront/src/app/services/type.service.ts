import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Type } from '../models/type.model';

@Injectable({
  providedIn: 'root',
})
export class TypeService {
  private baseUrl = 'http://localhost:8080/api/types';

  constructor(private http: HttpClient) {}

  getTypes(): Observable<Type[]> {
    return this.http.get<Type[]>(this.baseUrl);
  }

  getType(id: number): Observable<Type> {
    return this.http.get<Type>(`${this.baseUrl}/${id}`);
  }

  createType(type: Type): Observable<Type> {
    return this.http.post<Type>(this.baseUrl, type);
  }

  updateType(id: number, type: Type): Observable<Type> {
    return this.http.put<Type>(`${this.baseUrl}/${id}`, type);
  }

  deleteType(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
