package edu.kpi.comsys.dis.lab.services.impl;

import edu.kpi.comsys.dis.lab.entities.Dashboard;
import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.DashboardsRepository;
import edu.kpi.comsys.dis.lab.repositories.StoriesListRepository;
import edu.kpi.comsys.dis.lab.repositories.UserRepository;
import edu.kpi.comsys.dis.lab.services.DashboardService;
import edu.kpi.comsys.dis.lab.services.exceptions.DashboardNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    private DashboardsRepository dashboardsRepository;
    private StoriesListRepository listRepository;
    private UserRepository userRepository;

    @Autowired
    public DashboardServiceImpl(DashboardsRepository dashboardsRepository,
                                StoriesListRepository listRepository,
                                UserRepository userRepository) {
        this.dashboardsRepository = dashboardsRepository;
        this.listRepository = listRepository;
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
    public List<StoriesList> getDashboardLists(long dashboardId) throws EntityNotFoundException {
        if (!dashboardsRepository.exists(dashboardId)) {
            throw new DashboardNotFoundException(dashboardId);
        }
        return listRepository.findAllOnDashboard(dashboardId);
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
        Dashboard currentDashboard = dashboardsRepository.findOne(dashboard.getId());
        if (currentDashboard == null) {
            throw new DashboardNotFoundException(dashboard.getId());
        }
        Dashboard updatedDashboard = mergeDashboardUpdate(currentDashboard, dashboard);
        return dashboardsRepository.save(updatedDashboard);
    }

    private Dashboard mergeDashboardUpdate(Dashboard current, Dashboard update) {
        current.setTitle(update.getTitle());
        return current;
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

    @Transactional
    @Override
    public Set<User> getDashboardCoworkers(long dashboardId) throws EntityNotFoundException {
        Dashboard sharedDashboard = dashboardsRepository.findOne(dashboardId);
        if (sharedDashboard == null) {
            throw new DashboardNotFoundException(dashboardId);
        }
        return sharedDashboard.getCoworkers();
    }

    @Override
    public void setDashboardCoworkers(long dashboardId, Set<Long> usersIds) throws EntityNotFoundException {
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
