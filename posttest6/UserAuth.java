public interface UserAuth {
    boolean register(String username, String password);
    User login(String username, String password);
}
