package lt.myserver.backend.currencyConverter.services;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import lt.lb.webservices.fxrates.CcyISO4217;
import lt.lb.webservices.fxrates.FxRateTypeHandling;
import lt.lb.webservices.fxrates.FxRatesHandling;


@Service("fxRatesServiceImpl")
public class FxRatesApiServiceImpl{
    
    @Autowired
    private WebClient fxRatesApiClient;

    public FxRatesHandling getCurrentFxRates(String tp){
        return fxRatesApiClient
                .get()
                .uri("/getCurrentFxRates?tp="+ tp)
                .retrieve()
                .bodyToMono(FxRatesHandling.class)
                .block(); 
    }

    public FxRatesHandling getFxRates(String tp, Date date){
        String ISO_8601_date = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return fxRatesApiClient
                .get()
                .uri("/getFxRates?tp=" + tp + "&dt=" + ISO_8601_date)
                .retrieve()
                .bodyToMono(FxRatesHandling.class)
                .block(); 
    }

    public CcyISO4217[] getCurrencyList(){
        return CcyISO4217.values();
    }

    public FxRateTypeHandling[] getFxRateTypeList(){
        return FxRateTypeHandling.values();
    }
    
}