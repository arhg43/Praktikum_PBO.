import java.util.ArrayList;
import java.util.List;

public class AuthSystem implements UserAuth, Loggable {
    private List<User> users = new ArrayList<>();

    public AuthSystem() {
        // Tambah user default
        users.add(new User("admin", "admin", "admin"));
    }

    @Override
    public boolean register(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                logError("Registrasi gagal: username sudah ada.");
                return false;
            }
        }
        users.add(new User(username, password, "member"));
        logInfo("Registrasi berhasil untuk user: " + username);
        return true;
    }

    @Override
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                logInfo("Login berhasil: " + username);
                return user;
            }
        }
        logError("Login gagal untuk user: " + username);
        return null;
    }

    @Override
    public void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    @Override
    public void logError(String error) {
        System.out.println("[ERROR] " + error);
    }
}
