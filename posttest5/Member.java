public class Member {
    private String username;

    public Member(String username) {
        this.username = username;
    }

    // Getter
    public String getUsername() {
        return username;
    }

    // Setter (jika ingin mengubah username)
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Member: " + username;
    }
}
