package edu.kpi.comsys.dis.lab.dto;

import edu.kpi.comsys.dis.lab.entities.User;
import edu.kpi.comsys.dis.lab.entities.UserInfo;

public class UserDTO implements EntityDTO<User> {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String organization;
    private String title;

    public UserDTO(Long id,
                   String email,
                   String password,
                   String name,
                   String organization,
                   String title) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.organization = organization;
        this.title = title;
    }

    @Override
    public void fillFromEntity(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = null;
        this.name = entity.getInfo().getName();
        this.organization = entity.getInfo().getOrganization();
        this.title = entity.getInfo().getTitle();
    }

    @Override
    public void fillEntity(User entity) {
        entity.setId(this.id);
        entity.setEmail(this.email);
        entity.setPassword(this.password);
        UserInfo info = new UserInfo(entity, this.name, this.organization, this.title);
        entity.setInfo(info);
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
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
