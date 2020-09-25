import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { CurrencyConverterModel } from './models/CurrencyConverterModel';
import { ConverterReturnModel } from './models/ConverterReturnModel';

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

  convertCurrency(postBody: CurrencyConverterModel){
    return this.http.post<ConverterReturnModel>(this.serviceUrl + 'convertCurrency', postBody, httpOptions);
  }
  
/*
  sendFile(data: FormData) {
    return this.http.post<>(this.serviceUrl + 'postfiles', data);
  }*/ 
}
