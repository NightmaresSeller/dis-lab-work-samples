package edu.kpi.comsys.dis.lab;

import edu.kpi.comsys.dis.lab.entities.Dashboard;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.DashboardsRepository;
import edu.kpi.comsys.dis.lab.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringDataApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DashboardsTests {

    @Autowired
    private DashboardsRepository dashboardsRepository;
    @Autowired
    private UserRepository userRepository;

    private User dev;
    private User qa;
    private User designer;
    private User productOwner;

    private Dashboard workflowDashboard;
    private Dashboard activeDashboard;
    private Dashboard inactiveDashboard;
    private Dashboard historyDashboard;

    @Before
    public void setUp() throws Exception {
        dev = addUser("dev@it.com");
        qa = addUser("qa@it.com");
        designer = addUser("designer@it.com");
        productOwner = addUser("boss@company.com");

        workflowDashboard = addDashboard(dev, "Workflow");
        activeDashboard = addDashboard(dev, "Active");
        inactiveDashboard = addDashboard(dev, "Inactive");
        historyDashboard = addDashboard(productOwner, "History");

        shareDashboard(workflowDashboard, qa, designer);
        shareDashboard(inactiveDashboard, qa, productOwner);
        shareDashboard(activeDashboard, designer);
        shareDashboard(historyDashboard, dev, qa, designer);
    }

    @Test
    public void testFindDashboardByTitle() throws Exception {
        List<Dashboard> foundDashboards = dashboardsRepository.findByTitleContaining("flow");
        Assert.assertThat(foundDashboards, contains(workflowDashboard));
    }

    @Test
    public void testGetSharedDashboards() throws Exception {
        List<Dashboard> sharedDashboards;
        sharedDashboards = dashboardsRepository.findAllSharedWithUser(dev.getId());
        Assert.assertThat(sharedDashboards, contains(historyDashboard));

        sharedDashboards = dashboardsRepository.findAllSharedWithUser(qa.getId());
        Assert.assertThat(sharedDashboards, containsInAnyOrder(workflowDashboard, inactiveDashboard, historyDashboard));

        sharedDashboards = dashboardsRepository.findAllSharedWithUser(designer.getId());
        Assert.assertThat(sharedDashboards, containsInAnyOrder(workflowDashboard, activeDashboard, historyDashboard));

        sharedDashboards = dashboardsRepository.findAllSharedWithUser(productOwner.getId());
        Assert.assertThat(sharedDashboards, contains(inactiveDashboard));
    }

    @Test
    public void testGetUserDashboards() throws Exception {
        List<Dashboard> userDashboards;
        userDashboards = dashboardsRepository.findByUserId(dev.getId());
        Assert.assertThat(userDashboards, containsInAnyOrder(workflowDashboard, inactiveDashboard, activeDashboard));

        userDashboards = dashboardsRepository.findByUserId(productOwner.getId());
        Assert.assertThat(userDashboards, contains(historyDashboard));

        userDashboards = dashboardsRepository.findByUserId(qa.getId());
        Assert.assertThat(userDashboards, empty());

        userDashboards = dashboardsRepository.findByUserId(designer.getId());
        Assert.assertThat(userDashboards, empty());
    }

    private Dashboard addDashboard(User user, String title) {
        Dashboard dashboard = new Dashboard(user, title);
        return dashboardsRepository.save(dashboard);
    }

    private User addUser(String email) {
        User user = new User(email, "password");
        return userRepository.save(user);
    }

    private void shareDashboard(Dashboard dashboard, User ...coworkers) {
        dashboard.getCoworkers().addAll(Arrays.asList(coworkers));
        Stream.of(coworkers).forEach(cw -> cw.getSharedDashboards().add(dashboard));
        dashboardsRepository.save(dashboard);
    }

}
