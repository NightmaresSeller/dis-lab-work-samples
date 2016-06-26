package edu.kpi.comsys.dis.lab.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StoriesList {

    @Column(name = "list_id")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Story> stories;

    public StoriesList() {
        this.stories = new ArrayList<>();
    }

    public StoriesList(String title) {
        this();
        this.title = title;
    }

    public StoriesList(User user) {
        this();
        this.user = user;
    }

    public StoriesList(User user, String title) {
        this();
        this.title = title;
        this.user = user;
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

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = new ArrayList<>(stories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoriesList list = (StoriesList) o;

        return id != null && id.equals(list.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StoriesList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

}
