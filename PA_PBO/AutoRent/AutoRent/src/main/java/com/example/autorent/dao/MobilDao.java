package com.example.autorent.dao;

import com.example.autorent.model.Mobil;
import java.util.List;

public interface MobilDao {
    void tambahMobil(Mobil mobil);
    Mobil cariMobilById(String idMobil);
    List<Mobil> getSemuaMobil();
    void updateMobil(Mobil mobil);
    void hapusMobil(String idMobil);
}