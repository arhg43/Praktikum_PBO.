public class Member extends User {

    public Member(String fullName, String address, String phoneNumber, String username, String password) {
        super(fullName, address, phoneNumber, username, password, "member");
    }

    public void viewSchedule() {
        System.out.println("Member " + getUsername() + " is viewing workout schedule.");
    }
}
