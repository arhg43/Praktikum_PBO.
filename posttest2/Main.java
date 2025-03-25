import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserAuth userAuth = new UserAuth();
        MemberCRUD memberCRUD = new MemberCRUD();
        ScheduleCRUD scheduleCRUD = new ScheduleCRUD();

        while (true) {
            System.out.println("\n=== Selamat Datang di Gym Fitness Jaya ===");
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) { // LOGIN
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                User user = userAuth.login(username, password);
                if (user == null) {
                    System.out.println("Login gagal! Username atau password salah.");
                } else {
                    if (user.getRole().equals("admin")) {
                        adminMenu(scanner, scheduleCRUD, memberCRUD);
                    } else {
                        memberMenu(scanner, scheduleCRUD);
                    }
                }
            } else if (choice == 2) { // REGISTRASI
                System.out.print("Masukkan username baru: ");
                String username = scanner.nextLine();
                System.out.print("Masukkan password baru: ");
                String password = scanner.nextLine();

                if (userAuth.register(username, password)) {
                    memberCRUD.addMember(username);
                    System.out.println("Registrasi berhasil! Silakan login.");
                } else {
                    System.out.println("Username sudah digunakan. Pilih username lain.");
                }
            } else if (choice == 3) {
                System.out.println("Terima kasih telah menggunakan sistem.");
                scanner.close();
                return;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void adminMenu(Scanner scanner, ScheduleCRUD scheduleCRUD, MemberCRUD memberCRUD) {
        while (true) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Tambah Jadwal Latihan");
            System.out.println("2. Lihat Jadwal Latihan");
            System.out.println("3. Hapus Jadwal Latihan");
            System.out.println("4. Lihat Anggota");
            System.out.println("5. Logout");
            System.out.print("Pilih menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Masukkan nama member: ");
                String memberName = scanner.nextLine();
                System.out.print("Jenis Latihan: ");
                String workoutType = scanner.nextLine();
                System.out.print("Tanggal Latihan (YYYY-MM-DD): ");
                String scheduleDate = scanner.nextLine();
                scheduleCRUD.addSchedule(memberName, workoutType, scheduleDate);
            } else if (choice == 2) {
                scheduleCRUD.showSchedules();
            } else if (choice == 3) {
                System.out.print("Masukkan indeks jadwal yang akan dihapus: ");
                int index = scanner.nextInt();
                scheduleCRUD.deleteSchedule(index);
            } else if (choice == 4) {
                memberCRUD.showMembers();
            } else if (choice == 5) {
                return;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void memberMenu(Scanner scanner, ScheduleCRUD scheduleCRUD) {
        System.out.println("\n=== Menu Member ===");
        scheduleCRUD.showSchedules();
    }
}
