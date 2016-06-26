package edu.kpi.comsys.dis.lab.services.exceptions;

public class StoryNotFoundException extends EntityNotFoundException {

    private final Long storyId;

    public StoryNotFoundException(Long storyId) {
        super("Story with id \"" + storyId + "\" not found");
        this.storyId = storyId;
    }

    public StoryNotFoundException(String message, Long storyId) {
        super(message);
        this.storyId = storyId;
    }

    public StoryNotFoundException(String message, Throwable cause, Long storyId) {
        super(message, cause);
        this.storyId = storyId;
    }

    public StoryNotFoundException(Throwable cause, Long storyId) {
        super("Story with id \"" + storyId + "\" not found", cause);
        this.storyId = storyId;
    }

    public Long getStoryId() {
        return storyId;
    }

}
