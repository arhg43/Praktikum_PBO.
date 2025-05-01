import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserAuth userAuth = new AuthSystem(); // Polymorphism
        MemberCRUD memberCRUD = new MemberCRUD();
        ScheduleCRUD scheduleCRUD = new ScheduleCRUD();

        while (true) {
            System.out.println("\n=== Selamat Datang di Gym Fitness Jaya ===");
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.println("3. Keluar");

            int choice = 0;

            try {
                System.out.print("Pilih menu: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // buang newline
            } catch (Exception e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.nextLine(); // clear buffer
                continue;
            }

            if (choice == 1) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                User user = userAuth.login(username, password);
                LoggableUtil.totalLogs++;
                LoggableUtil.logCount();

                if (user == null) {
                    System.out.println("Login gagal! Username atau password salah.");
                } else {
                    if (user.getRole().equals("admin")) {
                        adminMenu(scanner, scheduleCRUD, memberCRUD);
                    } else {
                        memberMenu(scanner, scheduleCRUD);
                    }
                }
            } else if (choice == 2) {
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
            System.out.println("4. Ubah Jadwal Latihan");
            System.out.println("5. Lihat Anggota");
            System.out.println("6. Logout");

            int choice = 0;
            try {
                System.out.print("Pilih menu: ");
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Input tidak valid.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Masukkan nama member: ");
                    String memberName = scanner.nextLine();
                    System.out.print("Jenis Latihan: ");
                    String workoutType = scanner.nextLine();
                    System.out.print("Tanggal Latihan (YYYY-MM-DD): ");
                    String scheduleDate = scanner.nextLine();
                    System.out.print("Nama Instruktur: ");
                    String instrukturName = scanner.nextLine();
                    scheduleCRUD.addSchedule(memberName, workoutType, scheduleDate, instrukturName);
                }
                case 2 -> scheduleCRUD.showSchedules();
                case 3 -> {
                    System.out.print("Masukkan indeks jadwal yang akan dihapus: ");
                    int index = scanner.nextInt();
                    scheduleCRUD.deleteSchedule(index);
                }
                case 4 -> {
                    System.out.print("Masukkan indeks jadwal yang ingin diubah: ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukkan jenis latihan baru: ");
                    String newWorkout = scanner.nextLine();
                    System.out.print("Masukkan tanggal latihan baru (YYYY-MM-DD): ");
                    String newDate = scanner.nextLine();
                    System.out.print("Masukkan nama instruktur baru: ");
                    String newInstruktur = scanner.nextLine();
                    scheduleCRUD.updateSchedule(index, newWorkout, newDate, newInstruktur);
                }
                case 5 -> memberCRUD.showMembers();
                case 6 -> { return; }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void memberMenu(Scanner scanner, ScheduleCRUD scheduleCRUD) {
        while (true) {
            System.out.println("\n=== Menu Member ===");
            System.out.println("1. Lihat Jadwal Latihan");
            System.out.println("2. Keluar");

            int choice = 0;
            try {
                System.out.print("Pilih menu: ");
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Input tidak valid.");
                scanner.nextLine();
                continue;
            }

            if (choice == 1) {
                scheduleCRUD.showSchedules();
            } else if (choice == 2) {
                System.out.println("Anda telah keluar.");
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
