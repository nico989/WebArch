package com.example.chat_system.model;

import java.io.Serializable;

public class User implements Serializable {
    private String username;

    public User() {
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
