import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { catchError } from "rxjs/operators";
import { Expense } from "./Expense";

@Injectable()
export class ExpenseService {

    constructor(private http : HttpClient) {}

    getExpenses() {
        return this.http.get<Expense>('/api/expenses')
        .pipe(catchError(this.handleError('getExpenses', [])));
    }
    
    addExpense(expense : Expense) : Observable<Expense> {
        
        let options = {
            headers : new HttpHeaders({'Content-Type' : 'application/json'})
        };

        return this.http.post<Expense>('/api/expenses', expense, options);
    }

    removeExpense(expense : Expense) {
        let url = '/api/expenses/' + expense.id;
        return this.http.delete<Expense>(url, {});
    }

    private handleError<T> (opertion='operation', result ?: T) {
        return (error : any) : Observable<T> => {
            console.error(error);
            return of(result as T);
        }
    }
}