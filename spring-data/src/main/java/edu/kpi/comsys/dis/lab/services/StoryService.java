package edu.kpi.comsys.dis.lab.services;

import edu.kpi.comsys.dis.lab.entities.Story;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;

public interface StoryService {

    Story getStory(long storyId);
    Story createStory(long userId, long listId, Story story) throws EntityNotFoundException;
    Story updateStory(Story story) throws EntityNotFoundException;
    boolean deleteStory(long storyId);
    boolean moveStoryToList(long storyId, long listId) throws EntityNotFoundException;

}