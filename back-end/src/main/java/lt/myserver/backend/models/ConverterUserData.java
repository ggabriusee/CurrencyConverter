package lt.myserver.backend.models;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class ConverterUserData {
    
    private BigDecimal amount;
    private String from;
    private String to;
    private String type;
    private Date date;
}
