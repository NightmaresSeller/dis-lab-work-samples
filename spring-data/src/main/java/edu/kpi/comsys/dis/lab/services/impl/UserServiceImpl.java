package edu.kpi.comsys.dis.lab.services.impl;

import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.UserRepository;
import edu.kpi.comsys.dis.lab.services.UserService;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityAlreadyExistsException;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registerUser(User user) throws EntityValidationException, EntityAlreadyExistsException {
        user.setId(null); // ensure that we add new user rather than update existing
        User registeredUser = userRepository.saveIfNotExists(user);
        if (registeredUser == null) {
            throw new EntityAlreadyExistsException("User already exists " + user);
        }
        return registeredUser;
    }

}
