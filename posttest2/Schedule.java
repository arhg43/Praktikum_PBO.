public class Schedule {
    private String memberName;
    private String workoutType;
    private String scheduleDate;

    public Schedule(String memberName, String workoutType, String scheduleDate) {
        this.memberName = memberName;
        this.workoutType = workoutType;
        this.scheduleDate = scheduleDate;
    }

    // Getter
    public String getMemberName() {
        return memberName;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    // Setter
    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    @Override
    public String toString() {
        return "Member: " + memberName + " | Latihan: " + workoutType + " | Tanggal: " + scheduleDate;
    }
}
