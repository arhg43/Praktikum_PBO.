package com.example.autorent.dao;

import com.example.autorent.model.Sewa;

import java.util.List;
import java.util.Optional;

public interface SewaDAO {
    void tambahSewa(Sewa sewa);
    List<Sewa> getSemuaSewa();
    void updateSewa(Sewa sewa);
    void hapusSewa(int idSewa);
    Sewa cariSewaById(int idSewa);
}
