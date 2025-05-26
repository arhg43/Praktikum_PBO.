package com.example.autorent.model;

import java.time.LocalDate;

public class Sewa {
    private int idSewa;
    private int idPenyewa;
    private String idMobil;
    private LocalDate tanggalSewa;
    private LocalDate tanggalPengembalian; // Tambahan
    private String status;
    private double totalHarga;

    public Sewa(int idSewa, int idPenyewa, String idMobil, LocalDate tanggalSewa, LocalDate tanggalPengembalian, String status, double totalHarga) {
        this.idSewa = idSewa;
        this.idPenyewa = idPenyewa;
        this.idMobil = idMobil;
        this.tanggalSewa = tanggalSewa;
        this.tanggalPengembalian = tanggalPengembalian;
        this.status = status;
        this.totalHarga = totalHarga;
    }

    public int getIdSewa() {
        return idSewa;
    }

    public int getIdPenyewa() {
        return idPenyewa;
    }

    public String getIdMobil() {
        return idMobil;
    }

    public LocalDate getTanggalSewa() {
        return tanggalSewa;
    }

    public LocalDate getTanggalPengembalian() {
        return tanggalPengembalian; // Tambahan
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }
}
