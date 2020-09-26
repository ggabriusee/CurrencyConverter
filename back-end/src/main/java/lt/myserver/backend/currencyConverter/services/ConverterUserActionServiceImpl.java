package lt.myserver.backend.currencyConverter.services;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.myserver.backend.currencyConverter.entities.ConverterUserAction;
import lt.myserver.backend.currencyConverter.repositories.ConverterUserActionRepository;
import lt.myserver.backend.models.ConverterUserData;

@Service("userActionServiceImpl")
public class ConverterUserActionServiceImpl implements ConverterUserActionService{

    @Autowired
    private ConverterUserActionRepository userActionsRepository;

    public void registerUserAction(ConverterUserData cd, BigDecimal convertedAmount){
        ConverterUserAction ua = createUserAction(cd, convertedAmount);       
        userActionsRepository.save(ua);
    }


    private ConverterUserAction createUserAction(ConverterUserData cd, BigDecimal convertedAmount){
        ConverterUserAction ua = new ConverterUserAction();
        ua.setAmount(cd.getAmount());
        ua.setConvertFrom(cd.getFrom());
        ua.setConvertTo(cd.getTo());
        ua.setResult(convertedAmount);
        ua.setActionDate(new Timestamp(System.currentTimeMillis()));
        return ua;
    }
    
}
