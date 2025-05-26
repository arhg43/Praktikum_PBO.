package com.example.autorent.dao;


import com.example.autorent.model.User;

import java.util.List;

import com.example.autorent.model.User;

import java.util.List;

public interface UserDAO {
    User login(String username, String password);

    boolean registerPenyewa(int idPenyewa, String username, String password, String noHP);

    List<User> getAllUsers();
}
