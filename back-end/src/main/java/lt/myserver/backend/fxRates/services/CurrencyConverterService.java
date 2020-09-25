package lt.myserver.backend.fxRates.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.lb.webservices.fxrates.CcyISO4217;
import lt.lb.webservices.fxrates.FxRateHandling;
import lt.lb.webservices.fxrates.FxRatesHandling;
import lt.myserver.backend.fxRates.entities.EuroExcRate;
import lt.myserver.backend.fxRates.repositories.EuroExcRateRepository;
import lt.myserver.backend.models.ConverterReturnData;
import lt.myserver.backend.models.ConverterUserData;
import lt.myserver.backend.userAction.UserActionServiceImpl;

@Service("currencyConverterService")
public class CurrencyConverterService {

    @Autowired
    private FxRatesApiServiceImpl fxRatesApiService;
    
    @Autowired
    private UserActionServiceImpl userActionService;
    
    @Autowired
    private EuroExcRateRepository euroExcRateRepository;

    private final String API_ERROR_MSG = "Nepavyko pasiekti Lietuvos banko.";

    public CcyISO4217[] getCurrencyList(){
        return CcyISO4217.values();
    }

    public ConverterReturnData getConverterReturnData(ConverterUserData cud){
        ConverterReturnData ret = new ConverterReturnData();
        
        //EuroExcRate eer = findEuroExcRateByCurrencyAndDate(cud.getTo(), new Date(System.currentTimeMillis()));

        //if( new today){
            FxRatesHandling fxRatesHandling = fxRatesApiService.getCurrentFxRates();
            if (fxRatesHandling == null){
                ret.createError(API_ERROR_MSG);
                return ret;
            }
            storeCurrentFxRates(fxRatesHandling.getFxRate());
        
        
        //BigDecimal excRate =  .getExcRate();
        
        //convertCurrency(ret, cud.getAmount(), excRate);
        userActionService.registerUserAction(cud, ret.getConvertedAmount());
        
        ret.checkForError();
        return ret;
    }

    public EuroExcRate findEuroExcRateByCurrencyAndDate(String currency, Date date){
        return euroExcRateRepository.findOneByCurrencyAndExcRateDate(currency, date);
    }

    public void convertCurrency(ConverterReturnData ret, BigDecimal amount, BigDecimal rate){
        ret.setConvertedAmount(amount.multiply(rate));
        //
    }

    private void storeCurrentFxRates(List<FxRateHandling> rates){
        for (FxRateHandling rate: rates){
            EuroExcRate eer = createEuroExcRate(rate);
            euroExcRateRepository.save(eer);
        }
    }

    private EuroExcRate createEuroExcRate(FxRateHandling rate){
        EuroExcRate eer = new EuroExcRate();
        eer.setCurrency(rate.getCcyAmt().get(1).getCcy().name());
        eer.setExcRate(rate.getCcyAmt().get(1).getAmt());
        eer.setExcRateDate(rate.getDt().toGregorianCalendar().getTime());
        eer.setRateType(rate.getTp().name());
        return eer;
    }
    
}
