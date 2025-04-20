public class AuthSystem extends UserAuth {
    @Override
    public void displayUsers() {
        for (User user : users) {
            System.out.println("Username: " + user.getUsername() + " | Role: " + user.getRole());
        }
    }
}
