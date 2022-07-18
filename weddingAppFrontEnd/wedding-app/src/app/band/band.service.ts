import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Band } from './Band';

@Injectable()
export class BandService {
  constructor(private http: HttpClient) {}

  getBands() {
    return this.http
      .get<Band[]>('/api/bands')
      .pipe(catchError(this.handleError('getBands', [])));
  }

  addBand(band): Observable<Band> {
    let options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    };
    return this.http.post<Band>('/api/bands', band, options);
  }

  removeBand(band: Band) {
    let url = '/api/bands/' + band.id;
    return this.http.delete<Band>(url, {});
  }

  private handleError<T>(opertion = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
