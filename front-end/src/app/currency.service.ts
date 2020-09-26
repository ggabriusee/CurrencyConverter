import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { ConverterResponseModel } from './models/ConverterResponseModel';
import { ConverterRequestModel } from './models/ConverterRequestModel';

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

  getExcRateTypeList(){
    return this.http.get<string[]>(this.serviceUrl + 'getFxRateTypeList');
  }
  
  convertCurrency(postBody: ConverterRequestModel){
    return this.http.post<ConverterResponseModel>(this.serviceUrl + 'convertCurrency', postBody, httpOptions);
  }
  
}
