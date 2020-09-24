package lt.myserver.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.myserver.backend.models.UserActions;

@Repository
public interface UserActionsRepository extends CrudRepository<UserActions, Long> {
    
}
