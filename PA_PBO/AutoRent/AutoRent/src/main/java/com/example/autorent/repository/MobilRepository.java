package com.example.autorent.repository;

import com.example.autorent.dao.MobilDao;
import com.example.autorent.model.Mobil;
import com.example.autorent.model.Sewa;

import java.util.ArrayList;
import java.util.List;

public class MobilRepository implements MobilDao {
    private static MobilRepository instance;
    private final List<Mobil> daftarMobil = new ArrayList<>();

    public MobilRepository(){
        daftarMobil.add(new Mobil("B1234XYZ", "Toyota Avanza", 500000, true, "assets/images/B1234XYZ_avanza.png"));
        daftarMobil.add(new Mobil("B5678ABC", "Honda Brio", 600000, true, "assets/images/B5678ABC_brio.jpg"));
    }

    public static MobilRepository getInstance() {
        if (instance == null) {
            instance = new MobilRepository();
        }
        return instance;
    }

    @Override
    public void tambahMobil(Mobil mobil) {
        daftarMobil.add(mobil);
    }

    @Override
    public Mobil cariMobilById(String idMobil) {
        for (Mobil mobil : daftarMobil) {
            if (mobil.getIdMobil().equalsIgnoreCase(idMobil)) {
                return mobil;
            }
        }
        return null;
    }

    @Override
    public List<Mobil> getSemuaMobil() {
        return new ArrayList<>(daftarMobil); // Return salinan untuk mencegah modifikasi langsung
    }

    @Override
    public void updateMobil(Mobil mobilBaru) {
        for (int i = 0; i < daftarMobil.size(); i++) {
            Mobil mobilLama = daftarMobil.get(i);
            if (mobilLama.getIdMobil().equalsIgnoreCase(mobilBaru.getIdMobil())) {
                daftarMobil.set(i, mobilBaru);
                return;
            }
        }
    }

    @Override
    public void hapusMobil(String idMobil) {
        daftarMobil.removeIf(mobil -> mobil.getIdMobil().equalsIgnoreCase(idMobil));
    }

    public double calculateRentalPrice(Sewa sewa) {
        Mobil mobil = cariMobilById(sewa.getIdMobil());
        if (mobil != null && sewa.getTanggalSewa() != null && sewa.getTanggalPengembalian() != null) {
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                    sewa.getTanggalSewa(),
                    sewa.getTanggalPengembalian()
            );

            // Jika hasilnya 0 (tanggal sama), anggap 1 hari
            if (daysBetween == 0) {
                daysBetween = 1;
            }

            double totalHargaSewa = mobil.getHargaSewa() * daysBetween;
            sewa.setTotalHarga(totalHargaSewa); // simpan juga di objek sewa
            return totalHargaSewa;
        }
        return 0.0;
    }
}
