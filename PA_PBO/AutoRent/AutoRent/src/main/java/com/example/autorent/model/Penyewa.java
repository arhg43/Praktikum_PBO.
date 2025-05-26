package com.example.autorent.model;

public class Penyewa extends User {
    private String noHP;

    public Penyewa(int id, String username, String password, String noHP) {
        super(id, username, password);
        this.noHP = noHP;
    }

    public String getNoHP() {
        return noHP;
    }

    @Override
    public String getRole() {
        return "Penyewa";
    }

    public int getIdPenyewa() {
        return getId();
    }
}

