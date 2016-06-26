package edu.kpi.comsys.dis.lab.dto;

import edu.kpi.comsys.dis.lab.entities.Dashboard;

public class DashboardDTO implements EntityDTO<Dashboard> {

    private Long id;
    private String title;

    public DashboardDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public void fillFromEntity(Dashboard entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }

    @Override
    public void fillEntity(Dashboard entity) {
        entity.setId(this.id);
        entity.setTitle(this.title);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "DashboardDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

}
