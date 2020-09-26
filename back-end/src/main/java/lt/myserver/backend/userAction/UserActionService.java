package lt.myserver.backend.userAction;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lt.myserver.backend.models.ConverterUserData;

@Component
public interface UserActionService {
    
    public void registerUserAction(ConverterUserData cd, BigDecimal convertedAmount);
}
