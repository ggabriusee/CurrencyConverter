package lt.myserver.backend.fxRates.services;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.stereotype.Component;

import lt.myserver.backend.models.ConverterReturnData;
import lt.myserver.backend.models.ConverterUserData;

@Component
public interface CurrencyConverterService {

    public void convertByDate(ConverterUserData cud, ConverterReturnData ret);

    public void convertToCurrent(ConverterUserData cud, ConverterReturnData ret, Date today);

    public void convertCurrency(ConverterReturnData ret, BigDecimal amount, BigDecimal rate);   
}