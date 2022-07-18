import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Todo } from './Todo';

@Injectable()
export class TodoService {
  constructor(private http: HttpClient) {}

  getTodos() {
    return this.http
      .get<Todo>('/api/todos')
      .pipe(catchError(this.handleError('getTodos', [])));
  }

  addTodo(todo: Todo): Observable<Todo> {
    let options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    };

    return this.http.post<Todo>('/api/todos', todo, options);
  }

  removeTodo(todo: Todo) {
    let url = '/api/todos/' + todo.id;
    return this.http.delete<Todo>(url, {});
  }

  private handleError<T>(opertion = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
