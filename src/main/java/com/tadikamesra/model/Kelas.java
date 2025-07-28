package com.tadikamesra.model;

public class Kelas {
    private int kelasId;
    private String namaKelas;
    private int waliKelasId;

    // Getter
    public int getKelasId() {
        return kelasId;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public int getWaliKelasId() {
        return waliKelasId;
    }

    // Setter
    public void setKelasId(int kelasId) {
        this.kelasId = kelasId;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public void setWaliKelasId(int waliKelasId) {
        this.waliKelasId = waliKelasId;
    }
}