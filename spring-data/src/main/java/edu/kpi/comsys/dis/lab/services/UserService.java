package edu.kpi.comsys.dis.lab.services;

import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityAlreadyExistsException;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityValidationException;

public interface UserService {

    User getUser(long userId);
    User getUser(String email);
    User registerUser(User user) throws EntityValidationException, EntityAlreadyExistsException;


}
