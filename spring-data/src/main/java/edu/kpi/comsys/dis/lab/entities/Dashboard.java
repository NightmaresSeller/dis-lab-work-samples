package edu.kpi.comsys.dis.lab.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Dashboard {

    @Id
    @Column(name = "dashboard_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "dashboards", fetch = FetchType.LAZY)
    private List<StoriesList> lists;

    @ManyToMany(mappedBy = "sharedDashboards", fetch = FetchType.LAZY)
    private List<User> coworkers;

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

    public List<StoriesList> getLists() {
        return lists;
    }

    public void setLists(List<StoriesList> lists) {
        this.lists = lists;
    }

    public List<User> getCoworkers() {
        return coworkers;
    }

    public void setCoworkers(List<User> coworkers) {
        this.coworkers = coworkers;
    }

}
