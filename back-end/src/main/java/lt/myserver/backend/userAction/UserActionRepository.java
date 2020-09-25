package lt.myserver.backend.userAction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserActionRepository extends CrudRepository<UserAction, Long> {
    
}
