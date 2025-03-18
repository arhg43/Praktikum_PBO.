public class Member {
    private String username;

    public Member(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Member: " + username;
    }
}
