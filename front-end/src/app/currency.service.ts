import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  private serviceUrl = 'http://localhost:8080/';
  constructor(private http: HttpClient) {}

  getCurrentFxRates(){
    return this.http.get(this.serviceUrl + 'getCurrentFxRates');
  }

  getCurrencyList(){
    return this.http.get<string[]>(this.serviceUrl + 'getCurrencyList');
  }
/*
  sendInput(input: ){
    return this.http.post<>(this.serviceUrl + 'posttexts', input, httpOptions);
  }

  sendFile(data: FormData) {
    return this.http.post<>(this.serviceUrl + 'postfiles', data);
  }*/ 
}
