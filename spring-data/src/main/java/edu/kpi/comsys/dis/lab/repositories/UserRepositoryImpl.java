package edu.kpi.comsys.dis.lab.repositories;

import edu.kpi.comsys.dis.lab.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private UserRepository userRepository;

    @Transactional
    @Override
    public User saveIfNotExists(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            return userRepository.save(user);
        }
        return null;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
