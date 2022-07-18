import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Guest } from './Guest';

@Injectable()
export class GuestService {
  constructor(private http: HttpClient) {}

  getGuests(): Observable<Guest[]> {
    return this.http
      .get<Guest[]>('/api/guests')
      .pipe(catchError(this.handleError('getGuests', [])));
  }

  getGuest(username: string): Observable<Guest> {
    return this.http
      .get<Guest>('/api/guests/' + username)
      .pipe(catchError(this.handleError('getGuest', null)));
  }

  addGuest(guest): Observable<Guest> {
    let options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    };

    console.log(guest);

    return this.http.post<Guest>('/api/guests', guest, options);
  }

  removeGuest(guest: Guest) {
    let url = '/api/guests/' + guest.username;
    return this.http.delete<Guest>(url, {});
  }

  private handleError<T>(opertion = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
