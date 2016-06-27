package edu.kpi.comsys.dis.lab.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Dashboard {

    @Column(name = "dashboard_id")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "dashboards_lists",
            joinColumns={ @JoinColumn(name = "dashboard_id") },
            inverseJoinColumns={ @JoinColumn(name="list_id") }
    )
    private Set<StoriesList> lists;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "dashboards_sharing",
            joinColumns={ @JoinColumn(name = "dashboard_id") },
            inverseJoinColumns={ @JoinColumn(name="user_id") }
    )
    private Set<User> coworkers;

    public Dashboard() {
        this.lists = new LinkedHashSet<>();
        this.coworkers = new LinkedHashSet<>();
    }

    public Dashboard(String title) {
        this();
        this.title = title;
    }

    public Dashboard(User user) {
        this();
        this.user = user;
    }

    public Dashboard(User user, String title) {
        this();
        this.user = user;
        this.title = title;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<StoriesList> getLists() {
        return lists;
    }

    public void setLists(Set<StoriesList> lists) {
        this.lists = new LinkedHashSet<>(lists);
    }

    public Set<User> getCoworkers() {
        return coworkers;
    }

    public void setCoworkers(Set<User> coworkers) {
        this.coworkers = new LinkedHashSet<>(coworkers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dashboard dashboard = (Dashboard) o;

        return id != null && id.equals(dashboard.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Dashboard{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

}
