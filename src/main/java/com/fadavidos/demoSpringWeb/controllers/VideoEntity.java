package com.fadavidos.demoSpringWeb.controllers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class VideoEntity {

    private @Id @GeneratedValue Long id;
    private String name;
    private String description;
    private String username;

    protected VideoEntity(){
        this(null, null, null);
    }

    VideoEntity(String name, String description, String username) {
        this.id = null;
        this.description = description;
        this.name = name;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
