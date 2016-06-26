package edu.kpi.comsys.dis.lab.services;

import edu.kpi.comsys.dis.lab.entities.Dashboard;
import edu.kpi.comsys.dis.lab.services.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Set;

public interface DashboardService {

    Dashboard getDashboard(long dashboardId);
    List<Dashboard> getUserDashboards(long userId) throws EntityNotFoundException;
    List<Dashboard> getDashboardsSharedWithUser(long userId) throws EntityNotFoundException;
    Dashboard createDashboard(long userId, Dashboard dashboard) throws EntityNotFoundException;
    Dashboard updateDashboard(Dashboard dashboard) throws EntityNotFoundException;
    boolean deleteDashboard(long dashboardId);
    void setDashboardCoworkers(long dashboardId, Set<Long> usersIds) throws EntityNotFoundException;

}
