import java.util.ArrayList;

public class MemberCRUD {
    private ArrayList<Member> members = new ArrayList<>();

    public void addMember(String username) {
        members.add(new Member(username));
    }

    public void showMembers() {
        if (members.isEmpty()) {
            System.out.println("Belum ada anggota yang terdaftar.");
            return;
        }
        for (Member member : members) {
            System.out.println(member);
        }
    }
}
