package com.example.autorent.repository;

import com.example.autorent.dao.SewaDAO;
import com.example.autorent.model.Mobil;
import com.example.autorent.model.Sewa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SewaRepository implements SewaDAO {
    private static SewaRepository instance;
    private final List<Sewa> daftarSewa = new ArrayList<>();
    private final MobilRepository mobilRepository;



    private SewaRepository() {
        // Data dummy
        daftarSewa.add(new Sewa(1, 2, "B1234XYZ", LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 5), "Selesai",0));
        daftarSewa.add(new Sewa(2, 2, "B5678ABC", LocalDate.of(2025, 5, 2), LocalDate.of(2025, 5, 6), "Dibatalkan",0));
        this.mobilRepository = MobilRepository.getInstance();
    }

    public static SewaRepository getInstance() {
        if (instance == null) {
            instance = new SewaRepository();
        }
        return instance;
    }

    @Override
    public void tambahSewa(Sewa sewa) {
        daftarSewa.add(sewa);
    }

    @Override
    public List<Sewa> getSemuaSewa() {
        return new ArrayList<>(daftarSewa);
    }

    @Override
    public void updateSewa(Sewa sewaBaru) {
        for (int i = 0; i < daftarSewa.size(); i++) {
            Sewa s = daftarSewa.get(i);
            if (s.getIdSewa() == sewaBaru.getIdSewa()) {
                daftarSewa.set(i, sewaBaru);
                return;
            }
        }
    }

    @Override
    public void hapusSewa(int idSewa) {
        daftarSewa.removeIf(sewa -> sewa.getIdSewa() == idSewa);
    }

    @Override
    public Sewa cariSewaById(int idSewa) {
        for (Sewa sewa : daftarSewa) {
            if (sewa.getIdSewa() == idSewa) {
                return sewa;
            }
        }
        return null;
    }
    public void updateSewaStatus(Sewa sewa, String newStatus) {
        sewa.setStatus(newStatus);
        updateSewa(sewa);

        if ("Selesai".equals(newStatus) || "Dibatalkan".equals(newStatus)) {
            Mobil mobil = mobilRepository.cariMobilById(sewa.getIdMobil());
            if (mobil != null) {
                System.out.println(mobil.isTersedia());
                mobil.setTersedia(true);
                mobilRepository.updateMobil(mobil);
                System.out.println(mobil.isTersedia());
            }
        }
    }

    public void periksaDanUpdateKetersediaanMobil() {
        LocalDate hariIni = LocalDate.now();

        for (Sewa sewa : daftarSewa) {
            Mobil mobil = mobilRepository.cariMobilById(sewa.getIdMobil());

            if (mobil != null && !sewa.getStatus().equals("Dibatalkan") && !sewa.getStatus().equals("Selesai")) {
                LocalDate mulaiBlokir = sewa.getTanggalSewa().minusDays(2);
                LocalDate akhirBlokir = sewa.getTanggalPengembalian().plusDays(2);

                if ((hariIni.isEqual(mulaiBlokir) || hariIni.isAfter(mulaiBlokir)) &&
                        (hariIni.isEqual(akhirBlokir) || hariIni.isBefore(akhirBlokir))) {
                    // Hari ini ada di rentang blokir (H-2 sampai H+2)
                    mobil.setTersedia(false);
                } else {
                    mobil.setTersedia(true);
                }
                mobilRepository.updateMobil(mobil);
            }
        }
    }

}
