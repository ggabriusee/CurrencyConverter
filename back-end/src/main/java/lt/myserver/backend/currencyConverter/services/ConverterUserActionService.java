package lt.myserver.backend.currencyConverter.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lt.myserver.backend.models.ConverterUserData;

@Component
public interface ConverterUserActionService {
    
    public void registerUserAction(ConverterUserData cd, BigDecimal convertedAmount);
}
