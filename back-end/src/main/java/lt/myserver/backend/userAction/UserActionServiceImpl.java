package lt.myserver.backend.userAction;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.myserver.backend.models.ConverterUserData;

@Service("userActionServiceImpl")
public class UserActionServiceImpl implements UserActionService{

    @Autowired
    private UserActionRepository userActionsRepository;

    public void registerUserAction(ConverterUserData cd, BigDecimal convertedAmount){
        UserAction ua = createUserAction(cd, convertedAmount);       
        userActionsRepository.save(ua);
    }


    private UserAction createUserAction(ConverterUserData cd, BigDecimal convertedAmount){
        UserAction ua = new UserAction();
        ua.setAmount(cd.getAmount());
        ua.setConvertFrom(cd.getFrom());
        ua.setConvertTo(cd.getTo());
        ua.setResult(convertedAmount);
        ua.setActionDate(new Timestamp(System.currentTimeMillis()));
        return ua;
    }
    
}
