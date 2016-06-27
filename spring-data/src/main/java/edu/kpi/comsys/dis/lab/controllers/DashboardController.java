package edu.kpi.comsys.dis.lab.controllers;

import edu.kpi.comsys.dis.lab.dto.DashboardDTO;
import edu.kpi.comsys.dis.lab.dto.ListDTO;
import edu.kpi.comsys.dis.lab.dto.UserDTO;
import edu.kpi.comsys.dis.lab.entities.Dashboard;
import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.services.DashboardService;
import edu.kpi.comsys.dis.lab.services.ListService;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private DashboardService dashboardService;
    private ListService listService;

    @Autowired
    public DashboardController(DashboardService dashboardService, ListService listService) {
        this.dashboardService = dashboardService;
        this.listService = listService;
    }

    @RequestMapping(value = "/{dashboardId}",
            method = RequestMethod.GET)
    public ResponseEntity<?> getDashboard(@PathVariable("dashboardId") long dashboardId) {
        Dashboard dashboard = dashboardService.getDashboard(dashboardId);
        if (dashboard != null) {
            DashboardDTO dashboardDto = new DashboardDTO();
            dashboardDto.fillFromEntity(dashboard);
            return ResponseEntity.ok(dashboardDto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Dashboard with id \"" + dashboardId + "\" not found");
        }
    }

    @RequestMapping(value = "/{dashboardId}/lists",
            method = RequestMethod.GET)
    public ResponseEntity<?> getDashboardLists(@PathVariable("dashboardId") long dashboardId) {
        try {
            List<StoriesList> lists = dashboardService.getDashboardLists(dashboardId);
            List<ListDTO> listsDto = lists.stream()
                    .map(list -> {
                        ListDTO listDto = new ListDTO();
                        listDto.fillFromEntity(list);
                        return listDto;
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(listsDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @RequestMapping(value = "/",
            params = {"user"},
            method = RequestMethod.POST)
    public ResponseEntity<?> createDashboard(@RequestParam("user") long userId,
                                             @RequestBody DashboardDTO dashboardDto) {
        Dashboard dashboard = new Dashboard();
        dashboardDto.fillEntity(dashboard);
        try {
            Dashboard createdDashboard = dashboardService.createDashboard(userId, dashboard);
            DashboardDTO createdDashboardDto = new DashboardDTO();
            createdDashboardDto.fillFromEntity(createdDashboard);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdDashboardDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{dashboardId}",
            method = RequestMethod.PUT)
    public ResponseEntity<?> updateDashboard(@PathVariable("dashboardId") long dashboardId,
                                        @RequestBody DashboardDTO dashboardDto) {
        Dashboard dashboard = new Dashboard();
        dashboardDto.setId(dashboardId);
        dashboardDto.fillEntity(dashboard);
        try {
            Dashboard updatedDashboard = dashboardService.updateDashboard(dashboard);
            DashboardDTO updatedDashboardDto = new DashboardDTO();
            updatedDashboardDto.fillFromEntity(updatedDashboard);
            return ResponseEntity
                    .ok(updatedDashboardDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{dashboardId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDashboard(@PathVariable("dashboardId") long dashboardId) {
        if (dashboardService.deleteDashboard(dashboardId)) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Dashboard with id \"" + dashboardId + "\" not found");
        }
    }

    @RequestMapping(value = "/{dashboardId}/lists/add",
            params = {"list"},
            method = RequestMethod.GET)
    public ResponseEntity<String> addList(@PathVariable("dashboardId") long dashboardId,
                                            @RequestParam("list") long listId) {
        try {
            listService.addListToDashboard(listId, dashboardId);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{dashboardId}/lists/remove",
            params = {"list"},
            method = RequestMethod.GET)
    public ResponseEntity<String> removeList(@PathVariable("dashboardId") long dashboardId,
                                            @RequestParam("list") long listId) {
        try {
            listService.removeListFromDashboard(listId, dashboardId);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{dashboardId}/share",
            method = RequestMethod.GET)
    public ResponseEntity<?> getDashboardCoworkers(@PathVariable("dashboardId") long dashboardId) {
        try {
            Set<User> coworkingUsers = dashboardService.getDashboardCoworkers(dashboardId);
            Set<UserDTO> coworkingUsersDto = coworkingUsers.stream()
                    .map(user -> {
                        UserDTO userDto = new UserDTO();
                        userDto.fillFromEntity(user);
                        return userDto;
                    }).collect(Collectors.toSet());
            return ResponseEntity.ok(coworkingUsersDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{dashboardId}/share",
            method = RequestMethod.POST)
    public ResponseEntity<?> setDashboardCoworkers(@PathVariable("dashboardId") long dashboardId,
                                                   @RequestBody Set<Long> usersIds) {

        try {
            dashboardService.setDashboardCoworkers(dashboardId, usersIds);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
