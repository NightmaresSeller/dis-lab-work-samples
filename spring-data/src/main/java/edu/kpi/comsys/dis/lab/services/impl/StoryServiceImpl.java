package edu.kpi.comsys.dis.lab.services.impl;

import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.Story;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.StoriesListRepository;
import edu.kpi.comsys.dis.lab.repositories.StoryRepository;
import edu.kpi.comsys.dis.lab.repositories.UserRepository;
import edu.kpi.comsys.dis.lab.services.StoryService;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.ListNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.StoryNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryServiceImpl implements StoryService {

    private StoryRepository storyRepository;
    private StoriesListRepository listRepository;
    private UserRepository userRepository;

    @Autowired
    public StoryServiceImpl(StoryRepository storyRepository,
                            StoriesListRepository listRepository,
                            UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.listRepository = listRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Story getStory(long storyId) {
        return storyRepository.findOne(storyId);
    }

    @Override
    public Story createStory(long userId, long listId, Story story) throws EntityNotFoundException {
        User creatorUser = userRepository.findOne(userId);
        if (creatorUser == null) {
            throw new UserNotFoundException(userId);
        }
        StoriesList targetList = listRepository.findOne(listId);
        if (targetList == null) {
            throw new ListNotFoundException(listId);
        }
        if (story == null) {
            story = new Story();
        } else {
            story.setId(null); // ensure that we add new story rather than update existing one
        }
        story.setUser(creatorUser);
        creatorUser.getStories().add(story);
        Story createdStory = storyRepository.save(story);
        targetList.getStories().add(createdStory);
        listRepository.save(targetList);
        return createdStory;
    }

    @Override
    public Story updateStory(Story story) throws EntityNotFoundException {
        if (!storyRepository.exists(story.getId())) {
            throw new StoryNotFoundException(story.getId());
        }
        return storyRepository.save(story);
    }

    @Override
    public boolean deleteStory(long storyId) {
        if (storyRepository.exists(storyId)) {
            storyRepository.delete(storyId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean moveStoryToList(long storyId, long listId) throws EntityNotFoundException {
        Story movingStory = storyRepository.findOne(storyId);
        if (movingStory == null) {
            throw new StoryNotFoundException(storyId);
        }
        StoriesList destinationList = listRepository.findOne(listId);
        if (destinationList == null) {
            throw new ListNotFoundException(listId);
        }
        StoriesList sourceList = movingStory.getList();
        if (destinationList.equals(sourceList)) {
            return false;
        }
        if (sourceList != null) {
            sourceList.getStories().remove(movingStory);
            listRepository.save(sourceList);
        }
        destinationList.getStories().add(movingStory);
        listRepository.save(destinationList);
        return true;
    }

}
