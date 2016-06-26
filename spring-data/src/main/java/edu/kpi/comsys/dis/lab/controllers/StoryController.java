package edu.kpi.comsys.dis.lab.controllers;

import edu.kpi.comsys.dis.lab.dto.StoryDTO;
import edu.kpi.comsys.dis.lab.entities.Story;
import edu.kpi.comsys.dis.lab.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/story")
public class StoryController {

    private StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @RequestMapping(value = "/{storyId}", method = RequestMethod.GET)
    public ResponseEntity<StoryDTO> getStory(@PathVariable("storyId") long storyId) {
        Story story = storyService.getStory(storyId);
        if (story != null) {
            StoryDTO storyDTO = new StoryDTO();
            storyDTO.fillFromEntity(story);
            return ResponseEntity.ok(storyDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/{storyId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteStory(@PathVariable("storyId") long storyId) {
        if (storyService.deleteStory(storyId)) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
