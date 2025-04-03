import java.util.ArrayList;

public class UserAuth {
    protected ArrayList<User> users = new ArrayList<>(); // Protected agar bisa diwarisi
    
    public UserAuth() {
        users.add(new Admin("Admin", "Admin Address", "0000", "admin", "123"));
    }

    public boolean register(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Username sudah ada
            }
        }
        users.add(new Member("Member", "Member Address", "1111", username, password));
        return true;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Login gagal
    }
}
