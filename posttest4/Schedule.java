public class Schedule {
    private String memberName;
    private String workoutType;
    private String scheduleDate;

    public Schedule(String memberName, String workoutType, String scheduleDate) {
        this.memberName = memberName;
        this.workoutType = workoutType;
        this.scheduleDate = scheduleDate;
    }

    // Getter and Setter
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    @Override
    public String toString() {
        return "Member: " + memberName + " | Latihan: " + workoutType + " | Tanggal: " + scheduleDate;
    }
}
