package edu.kpi.comsys.dis.lab;

import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringDataApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserTests {

    private static final Logger LOG = LoggerFactory.getLogger("UserTests");

    private static final String USER_EMAIL = "user@kpi.edu";
    private static final String USER_2_EMAIL = "anotheruser@kpi.edu";
    private static final String USER_3_EMAIL = "anotheranotheruser@kpi.edu";

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        addUser(USER_EMAIL);
        addUser(USER_2_EMAIL);
    }

    @Test
    public void testFindUserByEmail() throws Exception {
        User foundUser = userRepository.findByEmail(USER_EMAIL);
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(USER_EMAIL, foundUser.getEmail());
    }

    @Test
    public void testSaveIfNotExists() throws Exception {
        User duplicateUser = new User(USER_EMAIL, "secret");
        Assert.assertNull(userRepository.saveIfNotExists(duplicateUser));

        User validNewUser = new User(USER_3_EMAIL, "secret");
        Assert.assertNotNull(userRepository.saveIfNotExists(validNewUser));
    }

    private User addUser(String email) {
        User user = new User(email, "password");
        return userRepository.save(user);
    }

}
