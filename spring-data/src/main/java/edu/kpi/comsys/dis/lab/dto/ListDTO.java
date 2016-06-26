package edu.kpi.comsys.dis.lab.dto;

import edu.kpi.comsys.dis.lab.entities.StoriesList;

public class ListDTO implements EntityDTO<StoriesList> {

    private Long id;
    private String title;

    public ListDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public void fillFromEntity(StoriesList entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }

    @Override
    public void fillEntity(StoriesList entity) {
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
        return "ListDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

}
