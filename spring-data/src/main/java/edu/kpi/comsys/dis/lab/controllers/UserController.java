package edu.kpi.comsys.dis.lab.controllers;

import edu.kpi.comsys.dis.lab.dto.DashboardDTO;
import edu.kpi.comsys.dis.lab.dto.UserDTO;
import edu.kpi.comsys.dis.lab.entities.Dashboard;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.services.DashboardService;
import edu.kpi.comsys.dis.lab.services.UserService;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityAlreadyExistsException;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private DashboardService dashboardService;

    @Autowired
    public UserController(UserService userService, DashboardService dashboardService) {
        this.userService = userService;
        this.dashboardService = dashboardService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDto) {
        User user = new User();
        userDto.fillEntity(user);
        try {
            User registeredUser = userService.registerUser(user);
            UserDTO registeredUserDto = new UserDTO();
            registeredUserDto.fillFromEntity(registeredUser);
            return ResponseEntity.ok(registeredUserDto);
        } catch (EntityValidationException | EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("userId") long userId) {
        User user = userService.getUser(userId);
        if (user != null) {
            UserDTO userDto = new UserDTO();
            userDto.fillFromEntity(user);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id \"" + userId + "\" not found");
        }
    }

    @RequestMapping(value = "/{userId}/dashboards")
    public ResponseEntity<?> getUserDashboards(@PathVariable("userId") long userId) {
        try {
            List<Dashboard> dashboards = dashboardService.getUserDashboards(userId);
            List<DashboardDTO> dashboardsDto = dashboards.stream()
                    .map(dashboard -> {
                        DashboardDTO dashboardDto = new DashboardDTO();
                        dashboardDto.fillFromEntity(dashboard);
                        return dashboardDto;
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(dashboardsDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{userId}/dashboards/shared")
    public ResponseEntity<?> getDashboardsSharedWittUser(@PathVariable("userId") long userId) {
        try {
            List<Dashboard> dashboards = dashboardService.getDashboardsSharedWithUser(userId);
            List<DashboardDTO> dashboardsDto = dashboards.stream()
                    .map(dashboard -> {
                        DashboardDTO dashboardDto = new DashboardDTO();
                        dashboardDto.fillFromEntity(dashboard);
                        return dashboardDto;
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(dashboardsDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
