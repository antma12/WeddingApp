import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Videographer } from './Videographer';

@Injectable()
export class VideographerService {
  constructor(private http: HttpClient) {}

  getVideographers() {
    return this.http
      .get<Videographer[]>('/api/videographers')
      .pipe(catchError(this.handleError('getVideographers', [])));
  }

  addVideographer(videographer): Observable<Videographer> {
    let options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    };

    console.log(videographer);

    return this.http.post<Videographer>(
      '/api/videographers',
      videographer,
      options
    );
  }

  removeVideographer(videographer: Videographer) {
    let url = '/api/videographers/' + videographer.id;
    return this.http.delete<Videographer>(url, {});
  }

  private handleError<T>(opertion = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
