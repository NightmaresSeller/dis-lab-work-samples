package edu.kpi.comsys.dis.lab.services;

import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.Story;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;

import java.util.List;

public interface ListService {

    StoriesList getList(long listId);
    List<Story> getListStories(long listId) throws  EntityNotFoundException;
    StoriesList createList(long userId, long dashboardId, StoriesList list) throws EntityNotFoundException;
    StoriesList updateList(StoriesList list) throws EntityNotFoundException;
    boolean deleteList(long listId);
    /**
     * Adds list to dashboard
     * @param listId list identifier
     * @param dashboardId dashboard identifier
     * @return true if list was added to dashboard, false - if list was already on dashboard
     * @throws EntityNotFoundException if list or dashboard with given identifiers could not be found
     */
    boolean addListToDashboard(long listId, long dashboardId) throws EntityNotFoundException;
    /**
     * Removes list from dashboard
     * @param listId list identifier
     * @param dashboardId dashboard identifier
     * @return true if list was removed dashboard, false - if list was not on dashboard
     * @throws EntityNotFoundException if list or dashboard with given identifiers could not be found
     */
    boolean removeListFromDashboard(long listId, long dashboardId) throws EntityNotFoundException;

}
