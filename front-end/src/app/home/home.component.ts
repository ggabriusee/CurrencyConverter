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
  convertedAmount: number;
  currentCornverterModel: CurrencyConverterModel;
  errorMsg:string;

  constructor(private currencyService: CurrencyService) {
    this.currencyCodes$ = currencyService.getCurrencyList();
  }

  ngOnInit() {
  }

  onSubmit(formData: CurrencyConverterModel){
    this.currentCornverterModel = formData;
    this.currencyService.convertCurrency(formData)
    .subscribe( (returnedAmount: number) => {
      if (returnedAmount == 0)
        this.errorMsg="Šiuo metu nepavyksta gauti pasirinktų valiutų kursų.";
      else{
        this.convertedAmount = returnedAmount;
        this.errorMsg = null;
      }
      console.log("GRAZINO BACKEND: ", returnedAmount);
    });
  }

  changeFromTo(from, to){
    console.log("???", from);
    console.log("!!!", to);
    let t = from.control.value;
    from.control.value = to.control.value;
    to.control.value = t;
  }

}
