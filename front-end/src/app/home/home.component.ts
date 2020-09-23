import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CurrencyService } from '../currency.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currencyCodes$: Observable<string[]>;

  constructor(currencyService: CurrencyService) {
    this.currencyCodes$ = currencyService.getCurrencyList();
  }

  ngOnInit() {
  }

  onSubmit(formVal){
    console.log(">>>", formVal);
  }

}
