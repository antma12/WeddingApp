import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { catchError } from "rxjs/operators";
import { MassFunction } from "./MassFunction";

@Injectable()
export class MassFunctionService {
    
    constructor(private http: HttpClient){

    }

    getMassFunctions() {
        return this.http.get<MassFunction>('/api/massfunctions')
        .pipe(catchError(this.handleError('getMassFunctions', [])));
    }
    
    addMassFunction(massFunction : MassFunction) : Observable<MassFunction> {
        
        let options = {
            headers : new HttpHeaders({'Content-Type' : 'application/json'})
        };

        return this.http.post<MassFunction>('/api/massfunctions', massFunction, options);
    }

    removeMassFunction(massFunction : MassFunction) {
        let url = '/api/massfunctions/' + massFunction.id;
        return this.http.delete<MassFunction>(url, {});
    }

    private handleError<T> (opertion='operation', result ?: T) {
        return (error : any) : Observable<T> => {
            console.error(error);
            return of(result as T);
        }
    }
}