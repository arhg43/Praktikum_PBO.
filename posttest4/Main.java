import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserAuth userAuth = new UserAuth();
        
        while (true) {
            System.out.println("\n=== Selamat Datang di Gym Fitness Jaya ===");
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) { 
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                
                User user = userAuth.login(username, password);
                if (user == null) {
                    System.out.println("Login gagal! Username atau password salah.");
                } else {
                    if (user.getRole().equals("admin")) {
                        adminMenu(scanner, user);
                    } else {
                        memberMenu(scanner, user);
                    }
                }
            } else if (choice == 2) { 
                System.out.print("Masukkan username baru: ");
                String username = scanner.nextLine();
                System.out.print("Masukkan password baru: ");
                String password = scanner.nextLine();
                
                if (userAuth.register(username, password)) {
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

    public static void adminMenu(Scanner scanner, User user) {
        Admin admin = (Admin) user;  
        while (true) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Tambah Jadwal Latihan");
            System.out.println("2. Lihat Anggota");
            System.out.println("3. Lihat Semua Jadwal");
            System.out.println("4. Logout");
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

                // Panggil metode overload (bisa pilih salah satu)
                admin.addSchedule(memberName, workoutType, scheduleDate);
                // admin.addSchedule(new Schedule(memberName, workoutType, scheduleDate));
            } else if (choice == 2) {
                admin.viewMembers();
            } else if (choice == 3) {
                admin.viewSchedule(); // override
            } else if (choice == 4) {
                return;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void memberMenu(Scanner scanner, User user) {
        user.viewSchedule(); // polymorphism (override akan otomatis jalan)
    }
}
