package lt.myserver.backend.currencyConverter.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
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
import lt.myserver.backend.currencyConverter.entities.ExcRate;
import lt.myserver.backend.currencyConverter.repositories.ExcRateRepository;
import lt.myserver.backend.models.ConverterReturnData;
import lt.myserver.backend.models.ConverterUserData;


@CommonsLog
@Service("currencyConverterService")
public class CurrencyConverterServiceImpl implements CurrencyConverterService{

    @Autowired
    private FxRatesApiServiceImpl fxRatesApiService;
    
    @Autowired
    private ConverterUserActionServiceImpl userActionService;
    
    @Autowired
    private ExcRateRepository excRateRepository;

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
        if(today.toString().equals(cud.getDate().toString())){
            convertToCurrent(cud, ret, today);
        }else{
            convertByDate(cud, ret);
        }

        completeRequest(cud, ret);
        log.info("Įvykdyta konvertavimo užklausa");
        return ret;
    }

    public void convertByDate(ConverterUserData cud, ConverterReturnData ret){
        ExcRate er = excRateRepository.searchByExcRateDate(cud.getFrom(), cud.getTo(), cud.getType(), cud.getDate());
        
        if(er == null){
            FxRatesHandling fxRatesHandling = fxRatesApiService.getFxRates(cud.getType(), cud.getDate());
            requestRatesFromBank(fxRatesHandling, cud, ret);
        }else
            convertCurrency(ret, cud.getAmount(), er.getExcRate());
    }

    public void convertToCurrent(ConverterUserData cud, ConverterReturnData ret, Date today){
        List<ExcRate> erList = excRateRepository.searchByDateAdded(cud.getFrom(), cud.getTo(), cud.getType(), today);
        
        if(erList.isEmpty()){
            FxRatesHandling fxRatesHandling = fxRatesApiService.getCurrentFxRates(cud.getType());
            requestRatesFromBank(fxRatesHandling, cud, ret);
        }else{
            ExcRate er = findNewestExcRate(erList);
            convertCurrency(ret, cud.getAmount(), er.getExcRate());
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

    public ExcRate findNewestExcRate(List<ExcRate> erList){
        Comparator<ExcRate> cmp = new Comparator<ExcRate>() {
            @Override
            public int compare(ExcRate er1, ExcRate er2) {
                return er1.getExcRateDate().compareTo(er2.getExcRateDate());
            }
        };
        
        return Collections.max(erList, cmp);
    }

    private void searchFxRates(List<FxRateHandling> fxRates, ConverterUserData cud, ConverterReturnData crt){
        boolean found = false;
        List<ExcRate> excRates = new ArrayList<ExcRate>();
        for (FxRateHandling fxRate: fxRates){
            ExcRate er = createExcRate(fxRate);
            excRates.add(er);
            if(er.getCurrencyTo().equalsIgnoreCase(cud.getTo())){
                found = true;
                convertCurrency(crt, cud.getAmount(), er.getExcRate());
            }
        }

        if (found)
            saveExcRates(excRates);
    }

    private void saveExcRates(List<ExcRate> erList){
        excRateRepository.saveAll(erList);
        log.info("Valiutų santykiai išsaugoti duomenų bazėje");
    }

    private ExcRate createExcRate(FxRateHandling rate){
        ExcRate er = new ExcRate();
        er.setCurrencyFrom(rate.getCcyAmt().get(0).getCcy().name());
        er.setCurrencyTo(rate.getCcyAmt().get(1).getCcy().name());
        er.setExcRate(rate.getCcyAmt().get(1).getAmt());
        er.setDateAdded(new Date(System.currentTimeMillis()));
        er.setType(rate.getTp().name());

        Date excRateSqlDate = new Date(rate.getDt().toGregorianCalendar().getTime().getTime());
        er.setExcRateDate(excRateSqlDate);
        
        return er;
    }
    
}
