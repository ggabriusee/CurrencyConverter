import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CurrencyService } from '../currency.service';
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
    .subscribe( (returnedAmount: number) => {
      if (returnedAmount === 0){
        this.errorMsg="Šiuo metu nepavyksta gauti pasirinktų valiutų kursų.";
        this.convertedAmount = null;
      }else{
        this.convertedAmount = returnedAmount;
        this.errorMsg = null;
      }
      console.log("GRAZINO BACKEND: ", returnedAmount);
    });
  }

  checkFormData(formData: CurrencyConverterModel){
    if(formData.amount === 0)
      formData.amount = this.userAmount = 1;
    else if (formData.amount < 0)
      formData.amount = this.userAmount = Math.abs(formData.amount);
  }

}
