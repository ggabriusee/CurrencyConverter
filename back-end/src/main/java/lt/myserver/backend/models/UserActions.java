package lt.myserver.backend.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserActions {

    @Id
	@GeneratedValue
    private Long id;
    
    //@Column
    private BigDecimal amount;

    //@Column
    private String convertFrom;
    
    //@Column
    private String convertTo;

    //@Column
    private BigDecimal result;

    //@Column
    private Timestamp actionDate;
    
}
