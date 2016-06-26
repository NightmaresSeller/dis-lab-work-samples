package edu.kpi.comsys.dis.lab;

import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.Story;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.StoriesListRepository;
import edu.kpi.comsys.dis.lab.repositories.StoryRepository;
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

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

import static edu.kpi.comsys.dis.lab.repositories.specifications.StorySpecificationBuilder.story;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringDataApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StoryTests {

    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private StoriesListRepository listRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    DataSource dataSource;

    private StoriesList toDoList;
    private StoriesList inProgressList;
    private Story toDoFeatureStory;
    private Story toDoBugStory;
    private Story inProgressFeatureStory;
    private Story inProgressBugStory;
    private User productOwner;
    private User qa;

    @Before
    public void setUp() throws Exception {
        productOwner = addUser("boss@company.com");
        qa = addUser("qa@it.com");

        toDoList = addList(productOwner, "To Do");
        inProgressList = addList(productOwner, "In Progress");

        toDoFeatureStory = addStory(productOwner,
                "New Feature",
                "Product Owner",
                "Front-end Developer",
                "Cool new functionality",
                "");
        toDoBugStory = addStory(qa,
                "Bug",
                "QA",
                "Back-end Developer",
                "Something went wrong",
                "Steps to reproduce: ..." +
                        "Expected result: ..." +
                        "Actual result: ...");
        inProgressFeatureStory = addStory(productOwner,
                "New Feature",
                "Product Owner",
                "Designer",
                "Landing Page",
                "Cool new design");
        inProgressBugStory = addStory(qa,
                "Bug",
                "QA",
                "Front-end Developer",
                "Something went completely wrong",
                "Steps to reproduce: ..." +
                        "Expected result: ..." +
                        "Actual result: ...");
        addStoryToList(
                toDoFeatureStory,
                toDoList);
        addStoryToList(
                toDoBugStory,
                toDoList);
        addStoryToList(
                inProgressFeatureStory,
                inProgressList);
        addStoryToList(
                inProgressBugStory,
                inProgressList);
    }

    @Test
    public void testGetStoriesOfUser() throws Exception {
        List<Story> qaStories = storyRepository.findByUserId(qa.getId());
        Assert.assertThat(qaStories, containsInAnyOrder(toDoBugStory, inProgressBugStory));

        List<Story> productOwnerStories = storyRepository.findByUserId(productOwner.getId());
        Assert.assertThat(productOwnerStories, containsInAnyOrder(toDoFeatureStory, inProgressFeatureStory));
    }

    @Test
    public void testGetStoriesOfList() throws Exception {
        List<Story> toDoStories = storyRepository.findByListId(toDoList.getId());
        Assert.assertThat(toDoStories, containsInAnyOrder(toDoBugStory, toDoFeatureStory));

        List<Story> inProgressStories = storyRepository.findByListId(inProgressList.getId());
        Assert.assertThat(inProgressStories, containsInAnyOrder(inProgressBugStory, inProgressFeatureStory));
    }

    @Test
    public void testFindStory() throws Exception {
        List<Story> foundStories;

        foundStories = storyRepository.findAll(
                story().withReporter("QA").withTitleContaining("completely").build());
        Assert.assertThat(foundStories, contains(inProgressBugStory));

        foundStories = storyRepository.findAll(
                story().withDescriptionContaining("Expected result:").build());
        Assert.assertThat(foundStories, containsInAnyOrder(toDoBugStory, inProgressBugStory));

        foundStories = storyRepository.findAll(
                story().inList(toDoList).withType("New Feature").build());
        Assert.assertThat(foundStories, contains(toDoFeatureStory));
    }

    @Test
    @Transactional
    public void testMoveStoryBetweenLists() throws Exception {
        toDoList.getStories().remove(toDoBugStory);
        inProgressList.getStories().add(toDoBugStory);
        toDoBugStory.setList(inProgressList);
        listRepository.save(inProgressList);
        listRepository.save(toDoList);

        toDoBugStory = storyRepository.findOne(toDoBugStory.getId());
        Assert.assertEquals(inProgressList, toDoBugStory.getList());

        inProgressList = listRepository.findOne(inProgressList.getId());
        Assert.assertThat(inProgressList.getStories(), hasItem(toDoBugStory));
    }

    private User addUser(String email) {
        return userRepository.save(new User(email, "password"));
    }

    private Story addStory(User user,
                          String type,
                          String reporter,
                          String assignee,
                          String title,
                          String description) {
        Story story = new Story(user, type, reporter, assignee, title, description);
        user.getStories().add(story);
        return storyRepository.save(story);
    }

    private void addStoryToList(Story story, StoriesList list) {
        list.getStories().add(story);
        story.setList(list);
        listRepository.save(list);
    }

    private StoriesList addList(User user, String title) {
        StoriesList list = new StoriesList(user, title);
        return listRepository.save(list);
    }

}
