public class Admin extends User {

    public Admin(String fullName, String address, String phoneNumber, String username, String password) {
        super(fullName, address, phoneNumber, username, password, "admin");
    }

    // Overloading
    public void addSchedule(String memberName, String workoutType, String scheduleDate) {
        System.out.println("Admin " + getUsername() + " menambahkan jadwal latihan untuk " + memberName);
    }

    public void addSchedule(Schedule schedule) {
        System.out.println("Admin " + getUsername() + " menambahkan jadwal: " +
            schedule.getWorkoutType() + " untuk " + schedule.getMemberName() +
            " pada " + schedule.getScheduleDate());
    }

    @Override
    public void viewSchedule() {
        System.out.println("Admin " + getUsername() + " melihat semua jadwal anggota.");
    }

    public void viewMembers() {
        System.out.println("Viewing all members...");
    }
}
