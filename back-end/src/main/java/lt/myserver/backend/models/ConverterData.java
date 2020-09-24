package lt.myserver.backend.models;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ConverterData {
    
    private BigDecimal amount;
    private String from;
    private String to;
}