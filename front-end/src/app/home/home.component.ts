import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CurrencyService } from '../currency.service';
import { ConverterResponseModel } from '../models/ConverterResponseModel';
import { ConverterFormModel } from '../models/ConverterFormModel';
import { ConverterRequestModel } from '../models/ConverterRequestModel';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  currencyCodes$: Observable<string[]>;
  excRateTypes$: Observable<string[]>;
  selectedCurrencyCode = "USD";
  selectedExcRateType = "EU";
  userAmount: number = 1;
  convertedAmount: number;
  converterView: ConverterRequestModel;
  errorMsg: string;
  todaysDate: string;

  constructor(private currencyService: CurrencyService) {}

  ngOnInit(){
    this.currencyCodes$ = this.currencyService.getCurrencyList();
    this.excRateTypes$ = this.currencyService.getExcRateTypeList();
    this.todaysDate = new Date().toISOString().substring(0, 10);
  }


  onSubmit(formData: ConverterFormModel){
    this.clearView();
    let formView = new ConverterRequestModel(formData);
    this.checkFormView(formView);

    this.currencyService.convertCurrency(formView)
    .subscribe( (returnedData: ConverterResponseModel) => {
      this.converterView = formView;
      if (returnedData.isError){
        this.errorMsg=returnedData.errMsg;
      }else{
        this.convertedAmount = returnedData.convertedAmount;
      }
    });
  }

  clearView(){
    this.convertedAmount = null;
    this.errorMsg = null;
    this.converterView = null;
  }

  checkFormView(formData: ConverterRequestModel){
    if(formData.amount === 0)
      formData.amount = this.userAmount = 1;
    else if (formData.amount < 0)
      formData.amount = this.userAmount = Math.abs(formData.amount);
  }

}
