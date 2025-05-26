package com.example.autorent.model;

// User.java
public abstract class User {
    private int id;
    private String username;
    private String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public abstract String getRole(); // polymorphic method

    public String getInfo() {
        return getRole() + ": " + username;
    }
}

