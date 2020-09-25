package lt.myserver.backend.userAction;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserAction {

    @Id
	@GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String convertFrom;
    
    @Column(nullable = false)
    private String convertTo;

    @Column
    private BigDecimal result;

    @Column(nullable = false)
    private Timestamp actionDate;
    
}
