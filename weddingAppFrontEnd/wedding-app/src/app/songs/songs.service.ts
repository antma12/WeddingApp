import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Song } from './Song';

@Injectable()
export class SongService {
  constructor(private http: HttpClient) {}

  getSongs() {
    return this.http
      .get<Song>('/api/songs')
      .pipe(catchError(this.handleError('getSongs', [])));
  }

  addSong(song: Song): Observable<Song> {
    let options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    };

    return this.http.post<Song>('/api/songs', song, options);
  }

  removeSong(song: Song) {
    let url = '/api/songs/' + song.id;
    return this.http.delete<Song>(url, {});
  }

  private handleError<T>(opertion = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
