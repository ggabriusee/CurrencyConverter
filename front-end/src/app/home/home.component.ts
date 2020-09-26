import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CurrencyService } from '../currency.service';
import { ConverterReturnModel } from '../models/ConverterReturnModel';
import { CurrencyConverterModel } from '../models/CurrencyConverterModel';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent{

  currencyCodes$: Observable<string[]>;
  selectedCurrencyCode = "USD";
  userAmount: number = 1;
  convertedAmount: number;
  converterView: CurrencyConverterModel;
  errorMsg:string;

  constructor(private currencyService: CurrencyService) {
    this.currencyCodes$ = currencyService.getCurrencyList();
  }

  onSubmit(formData: CurrencyConverterModel){
    this.clearView();
    this.checkFormData(formData);

    this.currencyService.convertCurrency(formData)
    .subscribe( (returnedData: ConverterReturnModel) => {
      
      this.converterView = formData;
      if (returnedData.isError){
        this.errorMsg=returnedData.errMsg;
      }else{
        this.convertedAmount = returnedData.convertedAmount;
      }
      console.log(this.converterView, this.convertedAmount);
    });
  }

  clearView(){
    this.convertedAmount = null;
    this.errorMsg = null;
    this.converterView = null;
  }

  checkFormData(formData: CurrencyConverterModel){
    formData.amount = +formData.amount;
    if(formData.amount === 0)
      formData.amount = this.userAmount = 1;
    else if (formData.amount < 0)
      formData.amount = this.userAmount = Math.abs(formData.amount);
  }

}
