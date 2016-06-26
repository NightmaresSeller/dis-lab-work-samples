package edu.kpi.comsys.dis.lab.repositories;

import edu.kpi.comsys.dis.lab.entities.User;

public interface UserRepositoryCustom {

    User saveIfNotExists(User user);

}
