import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
  import { catchError } from 'rxjs/operators';
import { Ballroom } from './Ballroom';

@Injectable()
export class BallroomService {
  constructor(private http: HttpClient) {}

  getBallrooms() {
    return this.http
      .get(/*<Ballroom[]>*/ '/api/ballrooms')
      .pipe(catchError(this.handleError('getBallrooms', [])));
  }

  addBallroom(ballroom): Observable<Ballroom> {
    let options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    };

    console.log(ballroom);

    return this.http.post<Ballroom>('/api/ballrooms', ballroom, options);
  }

  updateBallroom(ballroom): Observable<Ballroom> {
    let options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    };

    console.log(ballroom);

    return this.http.put<Ballroom>(
      '/api/ballrooms/' + ballroom.id,
      ballroom,
      options
    );
  }

  removeBallroom(ballroom: Ballroom) {
    let url = '/api/ballrooms/' + ballroom.id;
    return this.http.delete<Ballroom>(url, {});
  }

  private handleError<T>(opertion = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
