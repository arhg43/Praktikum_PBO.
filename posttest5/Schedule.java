public class Schedule {
    private String memberName;
    private String workoutType;
    private String scheduleDate;
    private String instrukturName; // Atribut baru untuk nama instruktur

    public Schedule(String memberName, String workoutType, String scheduleDate, String instrukturName) {
        this.memberName = memberName;
        this.workoutType = workoutType;
        this.scheduleDate = scheduleDate;
        this.instrukturName = instrukturName;
    }

    // Getter dan Setter
    public String getMemberName() {
        return memberName;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public String getInstrukturName() {
        return instrukturName; // Getter untuk instrukturName
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public void setInstrukturName(String instrukturName) {
        this.instrukturName = instrukturName; // Setter untuk instrukturName
    }

    @Override
    public String toString() {
        return "Member: " + memberName + " | Latihan: " + workoutType + " | Tanggal: " + scheduleDate + " | Nama Instruktur: " + instrukturName;
    }
}
