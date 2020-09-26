package lt.myserver.backend.fxRates.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.myserver.backend.fxRates.entities.EuroExcRate;

public interface EuroExcRateRepository extends JpaRepository<EuroExcRate, Long> {

    List<EuroExcRate> findByCurrencyAndDateAdded(String currency, Date date);

    boolean existsByCurrency(String currency);

    EuroExcRate findOneByCurrencyAndDateAdded(String currency, Date date);

    EuroExcRate findOneByCurrencyAndExcRateDate(String currency, Date date);
}
