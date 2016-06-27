package edu.kpi.comsys.dis.lab.controllers;

import edu.kpi.comsys.dis.lab.dto.StoryDTO;
import edu.kpi.comsys.dis.lab.entities.Story;
import edu.kpi.comsys.dis.lab.services.StoryService;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/story")
public class StoryController {

    private StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @RequestMapping(value = "/{storyId}",
            method = RequestMethod.GET)
    public ResponseEntity<?> getStory(@PathVariable("storyId") long storyId) {
        Story story = storyService.getStory(storyId);
        if (story != null) {
            StoryDTO storyDto = new StoryDTO();
            storyDto.fillFromEntity(story);
            return ResponseEntity.ok(storyDto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Story with id \"" + storyId + "\" not found");
        }
    }

    @RequestMapping(value = "/",
            params = {"user", "list"},
            method = RequestMethod.POST)
    public ResponseEntity<?> createStory(@RequestParam("user") long userId,
                                         @RequestParam("list") long listId,
                                         @RequestBody StoryDTO storyDto) {
        Story story = new Story();
        storyDto.fillEntity(story);
        try {
            Story createdStory = storyService.createStory(userId, listId, story);
            StoryDTO createdStoryDto = new StoryDTO();
            createdStoryDto.fillFromEntity(createdStory);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdStoryDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{storyId}",
            method = RequestMethod.PUT)
    public ResponseEntity<?> updateStory(@PathVariable("storyId") long storyId,
                                         @RequestBody StoryDTO storyDto) {
        Story story = new Story();
        storyDto.setId(storyId);
        storyDto.fillEntity(story);
        try {
            Story createdStory = storyService.updateStory(story);
            StoryDTO updatedStoryDto = new StoryDTO();
            updatedStoryDto.fillFromEntity(createdStory);
            return ResponseEntity
                    .ok(updatedStoryDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{storyId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStory(@PathVariable("storyId") long storyId) {
        if (storyService.deleteStory(storyId)) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Story with id \"" + storyId + "\" not found");
        }
    }

    @RequestMapping(value = "/{storyId}/move",
            params = {"toList"},
            method = RequestMethod.GET)
    public ResponseEntity<String> moveStory(@PathVariable("storyId") long storyId,
                                              @RequestParam("toList") long listId) {
        try {
            storyService.moveStoryToList(storyId, listId);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
