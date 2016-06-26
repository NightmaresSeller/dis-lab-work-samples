package edu.kpi.comsys.dis.lab.repositories;

import edu.kpi.comsys.dis.lab.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

    User findByEmail(String email);

}
