package edu.kpi.comsys.dis.lab.entities;

import javax.persistence.*;

@Entity
public class Story {

    @Column(name = "story_id")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String type;
    private String reporter;
    private String assignee;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "list_id")
    private StoriesList list;

    public Story() {
    }

    public Story(User user) {
        this.user = user;
    }

    public Story(String type,
                 String reporter,
                 String assignee,
                 String title,
                 String description) {
        this.type = type;
        this.reporter = reporter;
        this.assignee = assignee;
        this.title = title;
        this.description = description;
    }

    public Story(User user,
                 String type,
                 String reporter,
                 String assignee,
                 String title,
                 String description) {
        this(type, reporter, assignee, title, description);
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public StoriesList getList() {
        return list;
    }

    public void setList(StoriesList list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Story story = (Story) o;

        return !(id != null ? !id.equals(story.id) : story.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", reporter='" + reporter + '\'' +
                ", assignee='" + assignee + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
