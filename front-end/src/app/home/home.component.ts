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
export class HomeComponent implements OnInit {

  currencyCodes$: Observable<string[]>;
  selectedCurrencyCode = "USD";
  userAmount: number = 1;
  convertedAmount: number;
  currentConverterModel: CurrencyConverterModel;
  errorMsg:string;

  constructor(private currencyService: CurrencyService) {
    this.currencyCodes$ = currencyService.getCurrencyList();
  }

  ngOnInit() {
  }

  onSubmit(formData: CurrencyConverterModel){
    this.checkFormData(formData);
    this.currentConverterModel = formData;
    this.currencyService.convertCurrency(formData)
    .subscribe( (returnedData: ConverterReturnModel) => {
      if (returnedData.isError){
        this.errorMsg=returnedData.errMsg;
        this.convertedAmount = null;
      }else{
        this.convertedAmount = returnedData.convertedAmount;
        this.errorMsg = null;
      }
      console.log("GRAZINO BACKEND: ", returnedData);
    });
  }

  checkFormData(formData: CurrencyConverterModel){
    if(formData.amount === 0)
      formData.amount = this.userAmount = 1;
    else if (formData.amount < 0)
      formData.amount = this.userAmount = Math.abs(formData.amount);
  }

}
