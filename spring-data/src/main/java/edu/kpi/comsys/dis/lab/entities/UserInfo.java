package edu.kpi.comsys.dis.lab.entities;

import javax.persistence.*;

@Entity
public class UserInfo {

    @Column(name="user_info_id")
    @Id
    private Long id;

    @Column(length = 45)
    private String name;
    @Column(length = 45)
    private String organization;
    @Column(length = 45)
    private String title;

    @OneToOne(mappedBy = "info")
    @JoinColumn(name = "user_info_id")
    @MapsId
    private User user;

    public UserInfo() {
    }

    public UserInfo(User user) {
        this.user = user;
    }

    public UserInfo(String name, String organization, String title) {
        this.name = name;
        this.organization = organization;
        this.title = title;
    }

    public UserInfo(User user, String name, String organization, String title) {
        this.user = user;
        this.name = name;
        this.organization = organization;
        this.title = title;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
