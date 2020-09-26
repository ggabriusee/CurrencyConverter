package lt.myserver.backend.currencyConverter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.myserver.backend.currencyConverter.entities.ConverterUserAction;


@Repository
public interface ConverterUserActionRepository extends CrudRepository<ConverterUserAction, Long> {
    
}
