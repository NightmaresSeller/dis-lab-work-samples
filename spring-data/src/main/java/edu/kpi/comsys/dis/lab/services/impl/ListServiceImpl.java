package edu.kpi.comsys.dis.lab.services.impl;

import edu.kpi.comsys.dis.lab.entities.Dashboard;
import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.Story;
import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.repositories.DashboardsRepository;
import edu.kpi.comsys.dis.lab.repositories.StoriesListRepository;
import edu.kpi.comsys.dis.lab.repositories.StoryRepository;
import edu.kpi.comsys.dis.lab.repositories.UserRepository;
import edu.kpi.comsys.dis.lab.services.ListService;
import edu.kpi.comsys.dis.lab.services.exceptions.DashboardNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.ListNotFoundException;
import edu.kpi.comsys.dis.lab.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListServiceImpl implements ListService {

    private StoriesListRepository listRepository;
    private StoryRepository storyRepository;
    private UserRepository userRepository;
    private DashboardsRepository dashboardsRepository;

    @Autowired
    public ListServiceImpl(StoriesListRepository listRepository,
                           StoryRepository storyRepository,
                           UserRepository userRepository,
                           DashboardsRepository dashboardsRepository) {
        this.listRepository = listRepository;
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.dashboardsRepository = dashboardsRepository;
    }

    @Override
    public StoriesList getList(long listId) {
        return listRepository.findOne(listId);
    }

    @Override
    public List<Story> getListStories(long listId) throws EntityNotFoundException {
        if (!listRepository.exists(listId)) {
            throw new ListNotFoundException(listId);
        }
        return storyRepository.findByListId(listId);
    }

    @Override
    public List<StoriesList> getListsOnDashboard(long dashboardId) throws EntityNotFoundException {
        if (!dashboardsRepository.exists(dashboardId)) {
            throw new DashboardNotFoundException(dashboardId);
        }
        return listRepository.findAllOnDashboard(dashboardId);
    }

    @Override
    public StoriesList createList(long userId, long dashboardId, StoriesList list) throws EntityNotFoundException {
        User creatorUser = userRepository.findOne(userId);
        if (creatorUser == null) {
            throw new UserNotFoundException(userId);
        }
        Dashboard targetDashboard = dashboardsRepository.findOne(dashboardId);
        if (targetDashboard == null) {
            throw new DashboardNotFoundException(dashboardId);
        }
        if (list == null) {
            list = new StoriesList();
        } else {
            list.setId(null); // ensure that we add new list rather than update existing one
        }
        list.setUser(creatorUser);
        creatorUser.getLists().add(list);
        StoriesList createdList = listRepository.save(list);
        targetDashboard.getLists().add(createdList);
        dashboardsRepository.save(targetDashboard);
        return createdList;
    }

    @Override
    public StoriesList updateList(StoriesList list) throws EntityNotFoundException {
        if (!listRepository.exists(list.getId())) {
            throw new ListNotFoundException(list.getId());
        }
        return listRepository.save(list);
    }

    @Override
    public boolean deleteList(long listId) {
        if (listRepository.exists(listId)) {
            listRepository.delete(listId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addListToDashboard(long listId, long dashboardId) throws EntityNotFoundException {
        Dashboard destinationDashboard = dashboardsRepository.findOne(dashboardId);
        if (destinationDashboard == null) {
            throw new DashboardNotFoundException(dashboardId);
        }
        StoriesList addingList = listRepository.findOne(listId);
        if (addingList == null) {
            throw new ListNotFoundException(listId);
        }
        if (destinationDashboard.getLists().add(addingList)) {
            dashboardsRepository.save(destinationDashboard);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeListFromDashboard(long listId, long dashboardId) throws EntityNotFoundException {
        Dashboard destinationDashboard = dashboardsRepository.findOne(dashboardId);
        if (destinationDashboard == null) {
            throw new DashboardNotFoundException(dashboardId);
        }
        StoriesList addingList = listRepository.findOne(listId);
        if (addingList == null) {
            throw new ListNotFoundException(listId);
        }
        if (destinationDashboard.getLists().remove(addingList)) {
            dashboardsRepository.save(destinationDashboard);
            return true;
        } else {
            return false;
        }
    }

}
