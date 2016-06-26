package edu.kpi.comsys.dis.lab.services.impl;

import edu.kpi.comsys.dis.lab.entities.Dashboard;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.DashboardsRepository;
import edu.kpi.comsys.dis.lab.repositories.UserRepository;
import edu.kpi.comsys.dis.lab.services.DashboardService;
import edu.kpi.comsys.dis.lab.services.exceptions.DashboardNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class DashboardServiceImpl implements DashboardService {

    private DashboardsRepository dashboardsRepository;
    private UserRepository userRepository;

    @Autowired
    public DashboardServiceImpl(DashboardsRepository dashboardsRepository, UserRepository userRepository) {
        this.dashboardsRepository = dashboardsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Dashboard getDashboard(long dashboardId) {
        return dashboardsRepository.findOne(dashboardId);
    }

    @Override
    public List<Dashboard> getUserDashboards(long userId) throws EntityNotFoundException {
        if (!userRepository.exists(userId)) {
            throw new UserNotFoundException(userId);
        }
        return dashboardsRepository.findByUserId(userId);
    }

    @Override
    public List<Dashboard> getDashboardsSharedWithUser(long userId) throws EntityNotFoundException {
        if (!userRepository.exists(userId)) {
            throw new UserNotFoundException(userId);
        }
        return dashboardsRepository.findAllSharedWithUser(userId);
    }

    @Override
    public Dashboard createDashboard(long userId, Dashboard dashboard) throws EntityNotFoundException {
        User creatorUser = userRepository.findOne(userId);
        if (creatorUser == null) {
            throw new UserNotFoundException(userId);
        }
        if (dashboard == null) {
            dashboard = new Dashboard();
        } else {
            dashboard.setId(null); // // ensure that we add new dashboard rather than update existing one
        }
        dashboard.setUser(creatorUser);
        creatorUser.getDashboards().add(dashboard);
        return dashboardsRepository.save(dashboard);
    }

    @Override
    public Dashboard updateDashboard(Dashboard dashboard) throws EntityNotFoundException {
        if (!dashboardsRepository.exists(dashboard.getId())) {
            throw new DashboardNotFoundException(dashboard.getId());
        }
        return dashboardsRepository.save(dashboard);
    }

    @Override
    public boolean deleteDashboard(long dashboardId) {
        if (dashboardsRepository.exists(dashboardId)) {
            dashboardsRepository.delete(dashboardId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setDashboardCoworkers(long dashboardId, Set<Long> usersIds) throws EntityNotFoundException{
        if (usersIds == null) {
            throw new NullPointerException("Users IDs should not be null");
        }
        Dashboard sharedDashboard = dashboardsRepository.findOne(dashboardId);
        if (sharedDashboard == null) {
            throw new DashboardNotFoundException(dashboardId);
        }
        Set<User> coworkingUsers = new HashSet<>(usersIds.size());
        User user;
        for (Long userId: usersIds) {
            user = userRepository.findOne(userId);
            if (user == null) {
                throw new UserNotFoundException(userId);
            }
            coworkingUsers.add(user);
        }
        sharedDashboard.setCoworkers(coworkingUsers);
        coworkingUsers.forEach(cw -> cw.getSharedDashboards().add(sharedDashboard));
        dashboardsRepository.save(sharedDashboard);
    }

}
