public class User extends Person {
    private String username;
    private String password;
    private String role; // "admin" or "member"

    public User(String fullName, String address, String phoneNumber, String username, String password, String role) {
        super(fullName, address, phoneNumber);  
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter and Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Polymorphism (Override)
    public void viewSchedule() {
        System.out.println("User " + getUsername() + " belum memiliki jadwal spesifik.");
    }
}
