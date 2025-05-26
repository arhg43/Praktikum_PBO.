package com.example.autorent.model;

public class Mobil {
    private String idMobil; // No Plat
    private String merkMobil;
    private double hargaSewa;
    private boolean tersedia;
    private String imagePath;

    public Mobil(String idMobil, String merkMobil, double hargaSewa, boolean tersedia, String imagePath) {
        this.idMobil = idMobil;
        this.merkMobil = merkMobil;
        this.hargaSewa = hargaSewa;
        this.tersedia = tersedia;
        this.imagePath = imagePath;
    }

    public String getIdMobil() {
        return idMobil;
    }

    public void setIdMobil(String idMobil) {
        this.idMobil = idMobil;
    }

    public String getMerkMobil() {
        return merkMobil;
    }

    public void setMerkMobil(String merkMobil) {
        this.merkMobil = merkMobil;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public void setHargaSewa(double hargaSewa) {
        this.hargaSewa = hargaSewa;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}