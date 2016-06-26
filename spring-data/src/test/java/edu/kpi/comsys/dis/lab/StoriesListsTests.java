package edu.kpi.comsys.dis.lab;

import edu.kpi.comsys.dis.lab.entities.Dashboard;
import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.DashboardsRepository;
import edu.kpi.comsys.dis.lab.repositories.StoriesListRepository;
import edu.kpi.comsys.dis.lab.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
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
    private Dashboard activeDashboard;
    private Dashboard inactiveDashboard;
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
        activeDashboard = addDashboard(user, "Active");
        inactiveDashboard = addDashboard(user, "Inactive");
        historyDashboard = addDashboard(user, "History");

        addListsToDashboard(workflowDashboard, toDoList, inProgressList, testingList);
        addListsToDashboard(historyDashboard, resolvedList, obsoleteList);
        addListsToDashboard(activeDashboard, inProgressList, testingList);
        addListsToDashboard(inactiveDashboard, toDoList, resolvedList, obsoleteList);
    }

    @Test
    public void testGetListsOnDashboard() throws Exception {
        List<StoriesList> foundLists;
        foundLists = listRepository.findAllOnDashboard(workflowDashboard.getId());
        Assert.assertThat(foundLists, containsInAnyOrder(toDoList, inProgressList, testingList));

        foundLists = listRepository.findAllOnDashboard(activeDashboard.getId());
        Assert.assertThat(foundLists, containsInAnyOrder(inProgressList, testingList));

        foundLists = listRepository.findAllOnDashboard(inactiveDashboard.getId());
        Assert.assertThat(foundLists, containsInAnyOrder(toDoList, resolvedList, obsoleteList));

        foundLists = listRepository.findAllOnDashboard(historyDashboard.getId());
        Assert.assertThat(foundLists, containsInAnyOrder(resolvedList, obsoleteList));
    }

    @Test
    public void testFindListByTitle() throws Exception {
        List<StoriesList> foundLists = listRepository.findByTitleContaining("Progress");
        Assert.assertThat(foundLists, contains(inProgressList));
    }

    @Test
    @Transactional
    public void testAddListToDashboard() throws Exception {
        workflowDashboard.getLists().add(resolvedList);
        dashboardsRepository.save(workflowDashboard);

        resolvedList = listRepository.findOne(resolvedList.getId());
        workflowDashboard = dashboardsRepository.findOne(workflowDashboard.getId());

        Assert.assertThat(workflowDashboard.getLists(), hasItem(resolvedList));
    }

    private StoriesList addList(User user, String title) {
        StoriesList list = new StoriesList(user, title);
        return listRepository.save(list);
    }

    private Dashboard addDashboard(User user, String title) {
        Dashboard dashboard = new Dashboard(user, title);
        return dashboardsRepository.save(dashboard);
    }

    private void addListsToDashboard(Dashboard dashboard, StoriesList ...list) {
        dashboard.getLists().addAll(Arrays.asList(list));
        dashboardsRepository.save(dashboard);
    }

    private User addUser(String email) {
        User user = new User(email, "password");
        return userRepository.save(user);
    }

}
