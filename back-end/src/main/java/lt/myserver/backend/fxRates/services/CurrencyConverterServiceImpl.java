package lt.myserver.backend.fxRates.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import lt.lb.webservices.fxrates.CcyISO4217;
import lt.lb.webservices.fxrates.FxRateTypeHandling;
import lt.lb.webservices.fxrates.FxRateHandling;
import lt.lb.webservices.fxrates.FxRatesHandling;
import lt.myserver.backend.fxRates.entities.EuroExcRate;
import lt.myserver.backend.fxRates.repositories.EuroExcRateRepository;
import lt.myserver.backend.models.ConverterReturnData;
import lt.myserver.backend.models.ConverterUserData;
import lt.myserver.backend.userAction.UserActionServiceImpl;

@CommonsLog
@Service("currencyConverterService")
public class CurrencyConverterServiceImpl implements CurrencyConverterService{

    @Autowired
    private FxRatesApiServiceImpl fxRatesApiService;
    
    @Autowired
    private UserActionServiceImpl userActionService;
    
    @Autowired
    private EuroExcRateRepository euroExcRateRepository;

    private final String API_ERROR_MSG = "Nepavyko prisijungti prie banko.";

    public CcyISO4217[] getCurrencyList(){
        return fxRatesApiService.getCurrencyList();
    }

    public FxRateTypeHandling[] getExcRateTypes(){
        return fxRatesApiService.getFxRateTypeList();
    }

    public ConverterReturnData convert(ConverterUserData cud){
        ConverterReturnData ret = new ConverterReturnData();
        Date today = new Date(System.currentTimeMillis());
        if(today.equals(cud.getDate())){
            convertToCurrent(cud, ret, today);
        }else{
            convertByDate(cud, ret);
        }

        completeRequest(cud, ret);
        log.info("Įvykdyta konvertavimo užklausa");
        return ret;
    }

    public void convertByDate(ConverterUserData cud, ConverterReturnData ret){
        EuroExcRate eer = euroExcRateRepository.findOneByCurrencyAndExcRateDate(cud.getTo(), cud.getDate());

        if(eer == null){
            FxRatesHandling fxRatesHandling = fxRatesApiService.getFxRates(cud.getType(), cud.getDate());
            requestRatesFromBank(fxRatesHandling, cud, ret);
        }else
            convertCurrency(ret, cud.getAmount(), eer.getExcRate());
    }

    public void convertToCurrent(ConverterUserData cud, ConverterReturnData ret, Date today){
        List<EuroExcRate> eerList = euroExcRateRepository.findByCurrencyAndDateAdded(cud.getTo(), today);

        if(eerList.isEmpty()){
            FxRatesHandling fxRatesHandling = fxRatesApiService.getCurrentFxRates(cud.getType());
            requestRatesFromBank(fxRatesHandling, cud, ret);
        }else{
            EuroExcRate eer = findNewestExcRate(eerList);
            convertCurrency(ret, cud.getAmount(), eer.getExcRate());
        }
    }

    public void requestRatesFromBank(FxRatesHandling fxRatesHandling, ConverterUserData cud, ConverterReturnData ret){
        if (fxRatesHandling == null){
            log.error(API_ERROR_MSG);
            ret.createError("Atsiprašome, įvyko klaida.");
        }else
            searchFxRates(fxRatesHandling.getFxRate(), cud, ret);
    }

    public void convertCurrency(ConverterReturnData ret, BigDecimal amount, BigDecimal rate){
        BigDecimal convertedAmount = amount.multiply(rate); 
        ret.setConvertedAmount(convertedAmount);
    }

    public void completeRequest(ConverterUserData cud, ConverterReturnData crd){
        userActionService.registerUserAction(cud, crd.getConvertedAmount());    
        crd.checkForError();
    }

    public EuroExcRate findNewestExcRate(List<EuroExcRate> eerList){
        Comparator<EuroExcRate> cmp = new Comparator<EuroExcRate>() {
            @Override
            public int compare(EuroExcRate eer1, EuroExcRate eer2) {
                return eer1.getExcRateDate().compareTo(eer2.getExcRateDate());
            }
        };
        
        return Collections.max(eerList, cmp);
    }

    private void searchFxRates(List<FxRateHandling> rates, ConverterUserData cud, ConverterReturnData crt){
        boolean found = false;
        List<EuroExcRate> euroRates = new ArrayList<EuroExcRate>();
        for (FxRateHandling rate: rates){
            EuroExcRate eer = createEuroExcRate(rate);
            euroRates.add(eer);
            if(eer.getCurrency().equalsIgnoreCase(cud.getTo())){
                found = true;
                convertCurrency(crt, cud.getAmount(), eer.getExcRate());
            }
        }

        if (found)
            saveEuroExcRates(euroRates);
    }

    private void saveEuroExcRates(List<EuroExcRate> eerList){
        euroExcRateRepository.saveAll(eerList);
        log.info("Euro santykiai išsaugoti duomenų bazėje");
    }

    private EuroExcRate createEuroExcRate(FxRateHandling rate){
        EuroExcRate eer = new EuroExcRate();
        eer.setCurrency(rate.getCcyAmt().get(1).getCcy().name());
        eer.setExcRate(rate.getCcyAmt().get(1).getAmt());
        eer.setDateAdded(new Date(System.currentTimeMillis()));
        eer.setType(rate.getTp().name());

        Date excRateSqlDate = new Date(rate.getDt().toGregorianCalendar().getTime().getTime());
        eer.setExcRateDate(excRateSqlDate);
        
        return eer;
    }
    
}
