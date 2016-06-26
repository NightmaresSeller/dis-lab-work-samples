package edu.kpi.comsys.dis.lab.services.exceptions;

public class DashboardNotFoundException extends EntityNotFoundException {

    private final Long dashboardId;

    public DashboardNotFoundException(Long dashboardId) {
        super("Dashboard with id \"" + dashboardId + "\" not found");
        this.dashboardId = dashboardId;
    }

    public DashboardNotFoundException(String message, Long dashboardId) {
        super(message);
        this.dashboardId = dashboardId;
    }

    public DashboardNotFoundException(String message, Throwable cause, Long dashboardId) {
        super(message, cause);
        this.dashboardId = dashboardId;
    }

    public DashboardNotFoundException(Throwable cause, Long dashboardId) {
        super("Dashboard with id \"" + dashboardId + "\" not found", cause);
        this.dashboardId = dashboardId;
    }

    public Long getDashboardId() {
        return dashboardId;
    }

}
