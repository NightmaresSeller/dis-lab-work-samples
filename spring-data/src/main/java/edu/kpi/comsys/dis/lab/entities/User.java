package edu.kpi.comsys.dis.lab.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(unique = true, length = 255)
    private String email;
    @Column(length = 16)
    private String password;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInfo info;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Story> stories;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StoriesList> lists;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Dashboard> dashboards;
    @ManyToMany(mappedBy = "coworkers", fetch = FetchType.LAZY)
    private Set<Dashboard> sharedDashboards;

    public User() {
        this.info = new UserInfo(this);
        this.stories = new LinkedHashSet<>();
        this.lists = new LinkedHashSet<>();
        this.dashboards = new LinkedHashSet<>();
        this.sharedDashboards = new LinkedHashSet<>();
    }

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public Set<Story> getStories() {
        return stories;
    }

    public void setStories(Set<Story> stories) {
        this.stories = new LinkedHashSet<>(stories);
    }

    public Set<StoriesList> getLists() {
        return lists;
    }

    public void setLists(Set<StoriesList> lists) {
        this.lists = new LinkedHashSet<>(lists);
    }

    public Set<Dashboard> getDashboards() {
        return dashboards;
    }

    public void setDashboards(Set<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    public Set<Dashboard> getSharedDashboards() {
        return sharedDashboards;
    }

    public void setSharedDashboards(Set<Dashboard> sharedDashboards) {
        this.sharedDashboards = new LinkedHashSet<>(sharedDashboards);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", info=" + info +
                '}';
    }

}
