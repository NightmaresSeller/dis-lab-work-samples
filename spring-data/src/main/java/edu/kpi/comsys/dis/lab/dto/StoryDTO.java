package edu.kpi.comsys.dis.lab.dto;

import edu.kpi.comsys.dis.lab.entities.Story;

public class StoryDTO implements EntityDTO<Story> {

    private Long id;
    private String type;
    private String reporter;
    private String assignee;
    private String title;
    private String description;

    public StoryDTO() {
    }

    public StoryDTO(Long id,
                    String type,
                    String reporter,
                    String assignee,
                    String title,
                    String description) {
        this.id = id;
        this.type = type;
        this.reporter = reporter;
        this.assignee = assignee;
        this.title = title;
        this.description = description;
    }

    @Override
    public void fillFromEntity(Story entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.reporter = entity.getReporter();
        this.assignee = entity.getAssignee();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
    }

    @Override
    public void fillEntity(Story entity) {
        entity.setId(this.id);
        entity.setType(this.type);
        entity.setReporter(this.reporter);
        entity.setAssignee(this.assignee);
        entity.setTitle(this.title);
        entity.setDescription(this.description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "StoryDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", reporter='" + reporter + '\'' +
                ", assignee='" + assignee + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
