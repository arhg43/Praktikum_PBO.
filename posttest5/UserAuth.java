import java.util.ArrayList;

public abstract class UserAuth {
    protected ArrayList<User> users = new ArrayList<>();

    public UserAuth() {
        users.add(new User("admin", "123", "admin")); // default
    }

    public boolean register(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
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
        return null;
    }

    public abstract void displayUsers(); // Abstract method
}
