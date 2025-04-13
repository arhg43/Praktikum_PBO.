public class Member extends User {

    public Member(String fullName, String address, String phoneNumber, String username, String password) {
        super(fullName, address, phoneNumber, username, password, "member");
    }

    @Override
    public void viewSchedule() {
        System.out.println("Member " + getUsername() + " melihat jadwal latihan pribadinya.");
    }
}
