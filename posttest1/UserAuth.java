import java.util.ArrayList;

public class UserAuth {
    private ArrayList<User> users = new ArrayList<>();

    public UserAuth() {
        users.add(new User("admin", "123", "admin")); // Admin default
    }

    public boolean register(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Username sudah ada
            }
        }
        users.add(new User(username, password, "member"));
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
