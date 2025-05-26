package com.example.autorent.repository;

import com.example.autorent.dao.UserDAO;
import com.example.autorent.model.Admin;
import com.example.autorent.model.Penyewa;
import com.example.autorent.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserDAO {
    private static UserRepository instance;
    private final List<User> users = new ArrayList<>();
    private User currentUser;

    public UserRepository() {
        users.add(new Admin(1, "admin1", "admin123"));
        users.add(new Penyewa(2, "user1", "user123", "08123456789"));
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    @Override
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login berhasil: " + user.getInfo()); // polymorphism!
                return user;
            }
        }
        return null;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Integer getCurrentPenyewaId() {
        // Polymorphic casting is still necessary if we need data only Penyewa has
        if (currentUser instanceof Penyewa penyewa) {
            return penyewa.getIdPenyewa();
        }
        return null;
    }

    private boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    @Override
    public boolean registerPenyewa(int idPenyewa, String username, String password, String noHP) {
        if (isUsernameTaken(username)) {
            return false;
        }
        users.add(new Penyewa(idPenyewa, username, password, noHP));
        return true;
    }

    public Penyewa cariPenyewaById(int idPenyewa) {
        for (User user : users) {
            if (user instanceof Penyewa penyewa && penyewa.getIdPenyewa() == idPenyewa) {
                return penyewa;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
