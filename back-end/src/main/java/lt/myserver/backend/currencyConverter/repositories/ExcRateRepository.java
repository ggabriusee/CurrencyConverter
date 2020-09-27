package lt.myserver.backend.currencyConverter.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.myserver.backend.currencyConverter.entities.ExcRate;

public interface ExcRateRepository extends JpaRepository<ExcRate, Long> {

    @Query("select u from ExcRate u where u.currencyFrom = ?1 and u.currencyTo = ?2 and u.type = ?3 and u.dateAdded = ?4")
    List<ExcRate> searchByDateAdded(String currencyFrom, String currencyTo, String type, Date dateAdded);

    List<ExcRate> findByCurrencyToAndDateAdded(String currency, Date date);

    boolean existsByCurrencyTo(String currency);

    ExcRate findOneByCurrencyToAndDateAdded(String currency, Date date);

    ExcRate findOneByCurrencyToAndExcRateDate(String currency, Date date);

    @Query("select u from ExcRate u where u.currencyFrom = ?1 and u.currencyTo = ?2 and u.type = ?3 and u.excRateDate = ?4")
    ExcRate searchByExcRateDate(String currencyFrom, String currencyTo, String type, Date excRateDate);
}
