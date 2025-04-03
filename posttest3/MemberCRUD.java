import java.util.ArrayList;

public class MemberCRUD {
    private ArrayList<Member> members = new ArrayList<>();

    // Menambahkan anggota baru
    public void addMember(String username, String fullName, String address, String phoneNumber) {
        members.add(new Member(fullName, address, phoneNumber, username, "defaultPassword"));
        System.out.println("Anggota " + username + " berhasil ditambahkan.");
    }

    // Menampilkan semua anggota
    public void showMembers() {
        if (members.isEmpty()) {
            System.out.println("Belum ada anggota yang terdaftar.");
            return;
        }
        for (Member member : members) {
            System.out.println(member);
        }
    }

    // Getter untuk mengambil daftar anggota
    public ArrayList<Member> getMembers() {
        return members;
    }
}
