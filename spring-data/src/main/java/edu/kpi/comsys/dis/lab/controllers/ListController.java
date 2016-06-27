package edu.kpi.comsys.dis.lab.controllers;

import edu.kpi.comsys.dis.lab.dto.ListDTO;
import edu.kpi.comsys.dis.lab.dto.StoryDTO;
import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.Story;
import edu.kpi.comsys.dis.lab.services.ListService;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
public class ListController {

    private ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    @RequestMapping(value = "/{listId}",
            method = RequestMethod.GET)
    public ResponseEntity<?> getList(@PathVariable("listId") long listId) {
        StoriesList list = listService.getList(listId);
        if (list != null) {
            ListDTO listDto = new ListDTO();
            listDto.fillFromEntity(list);
            return ResponseEntity.ok(listDto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("List with id \"" + listId + "\" not found");
        }
    }

    @RequestMapping(value = "/{listId}/stories",
            method = RequestMethod.GET)
    public ResponseEntity<?> getListStories(@PathVariable("listId") long listId) {
        try {
            List<Story> stories = listService.getListStories(listId);
            List<StoryDTO> storiesDto = stories.stream()
                    .map(story -> {
                        StoryDTO storyDto = new StoryDTO();
                        storyDto.fillFromEntity(story);
                        return storyDto;
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(storiesDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @RequestMapping(params = {"user", "dashboard"},
            method = RequestMethod.POST)
    public ResponseEntity<?> createList(@RequestParam("user") long userId,
                                        @RequestParam("dashboard") long dashboardId,
                                        @RequestBody ListDTO listDto) {
        StoriesList list = new StoriesList();
        listDto.fillEntity(list);
        try {
            StoriesList createdList = listService.createList(userId, dashboardId, list);
            ListDTO createdListDto = new ListDTO();
            createdListDto.fillFromEntity(createdList);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdListDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{listId}",
            method = RequestMethod.PUT)
    public ResponseEntity<?> updateList(@PathVariable("listId") long listId,
                                         @RequestBody ListDTO listDto) {
        StoriesList list = new StoriesList();
        listDto.setId(listId);
        listDto.fillEntity(list);
        try {
            StoriesList updatedList = listService.updateList(list);
            ListDTO updatedListDto = new ListDTO();
            updatedListDto.fillFromEntity(updatedList);
            return ResponseEntity
                    .ok(updatedListDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{listId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteList(@PathVariable("listId") long listId) {
        if (listService.deleteList(listId)) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("List with id \"" + listId + "\" not found");
        }
    }

}
