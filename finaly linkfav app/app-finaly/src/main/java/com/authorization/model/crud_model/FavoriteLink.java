package com.authorization.model.crud_model;

import javax.persistence.*;

@Entity
@Table(name = "favlinks",schema = "finally")
public class FavoriteLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String describe;
    private String link;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "FavoriteLink{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", describe='" + describe + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
