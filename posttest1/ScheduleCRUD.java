import java.util.ArrayList;

public class ScheduleCRUD {
    private ArrayList<Schedule> schedules = new ArrayList<>();

    public void addSchedule(String memberName, String workoutType, String scheduleDate) {
        schedules.add(new Schedule(memberName, workoutType, scheduleDate));
        System.out.println("Jadwal latihan untuk " + memberName + " berhasil ditambahkan!");
    }

    public void showSchedules() {
        if (schedules.isEmpty()) {
            System.out.println("Belum ada jadwal latihan yang terdaftar.");
            return;
        }
        for (Schedule schedule : schedules) {
            System.out.println(schedule);
        }
    }

    public void deleteSchedule(int index) {
        if (index >= 0 && index < schedules.size()) {
            System.out.println("Jadwal latihan untuk " + schedules.get(index).getMemberName() + " berhasil dihapus.");
            schedules.remove(index);
        } else {
            System.out.println("Indeks tidak valid.");
        }
    }
}
