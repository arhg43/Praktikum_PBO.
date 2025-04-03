public class Admin extends User {

    public Admin(String fullName, String address, String phoneNumber, String username, String password) {
        super(fullName, address, phoneNumber, username, password, "admin");
    }

    public void addSchedule(String memberName, String workoutType, String scheduleDate) {
        System.out.println("Admin " + getUsername() + " added a workout schedule for " + memberName);
    }

    public void viewMembers() {
        System.out.println("Viewing all members...");
    }
}
