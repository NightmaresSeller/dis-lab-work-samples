package edu.kpi.comsys.dis.lab.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class StoriesList {

    @Id
    @Column(name = "list_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "list", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Story> stories;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "dashboards_lists",
            joinColumns={ @JoinColumn(name = "list_id") },
            inverseJoinColumns={ @JoinColumn(name="dashboard_id") }
    )
    private List<StoriesList> dashboards;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<StoriesList> getDashboards() {
        return dashboards;
    }

    public void setDashboards(List<StoriesList> dashboards) {
        this.dashboards = dashboards;
    }

}
