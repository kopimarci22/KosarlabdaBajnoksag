package com.example.kosarlabdabajnoksag.activities.Interface;

import java.util.Date;

public class UserImpl implements User{
    private String name;
    private String id;
    private String email;

    public UserImpl(String name, String id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
