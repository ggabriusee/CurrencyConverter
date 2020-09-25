package lt.myserver.backend.fxRates.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.myserver.backend.fxRates.entities.EuroExcRate;

public interface EuroExcRateRepository extends JpaRepository<EuroExcRate, Long> {

    List<EuroExcRate> findByCurrencyAndExcRateDate(String currency, Date date);

    EuroExcRate findOneByCurrencyAndExcRateDate(String currency, Date date);
}
