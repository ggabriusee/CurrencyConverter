package lt.myserver.backend.fxRates.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class EuroExcRate {
    
    @Id
	@GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private BigDecimal excRate;

    @Column(nullable = false)
    private Date excRateDate;

    @Column(nullable = false)
    private String rateType;

}
