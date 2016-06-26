package edu.kpi.comsys.dis.lab;

import edu.kpi.comsys.dis.lab.entities.Dashboard;
import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.DashboardsRepository;
import edu.kpi.comsys.dis.lab.repositories.StoriesListRepository;
import edu.kpi.comsys.dis.lab.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringDataApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StoriesListsTests {

    @Autowired
    private StoriesListRepository listRepository;
    @Autowired
    private DashboardsRepository dashboardsRepository;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private StoriesList toDoList;
    private StoriesList inProgressList;
    private StoriesList testingList;
    private StoriesList resolvedList;
    private StoriesList obsoleteList;

    private Dashboard workflowDashboard;
    private Dashboard historyDashboard;

    @Before
    public void setUp() throws Exception {
        user = addUser("user@kpi.ua");

        toDoList = addList(user, "To Do");
        inProgressList = addList(user, "In Progress");
        testingList = addList(user, "Testing");
        resolvedList = addList(user, "Resolved");
        obsoleteList = addList(user, "Obsolete");

        workflowDashboard = addDashboard(user, "Workflow");
        historyDashboard = addDashboard(user, "History");

        addListToDashboard(toDoList, workflowDashboard);
        addListToDashboard(inProgressList, workflowDashboard);
        addListToDashboard(testingList, workflowDashboard);
        addListToDashboard(resolvedList, historyDashboard);
        addListToDashboard(obsoleteList, historyDashboard);
    }

    @Test
    public void testGetListsOnDashboard() throws Exception {
        List<Dashboard> foundDashboard = dashboardsRepository.findByTitleContaining("flow");
        Assert.assertThat(foundDashboard, contains(workflowDashboard));
    }

    @Test
    public void testFindListByTitle() throws Exception {


    }

    private StoriesList addList(User user, String title) {
        StoriesList list = new StoriesList(user, title);
        return listRepository.save(list);
    }

    private Dashboard addDashboard(User user, String title) {
        Dashboard dashboard = new Dashboard(user, title);
        return dashboardsRepository.save(dashboard);
    }

    private void addListToDashboard(StoriesList list, Dashboard dashboard) {
        dashboard.getLists().add(list);
        list.getDashboards().add(dashboard);
        dashboardsRepository.save(dashboard);
    }

    private User addUser(String email) {
        User user = new User(email, "password");
        return userRepository.save(user);
    }

}
