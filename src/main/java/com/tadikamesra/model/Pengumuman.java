package com.tadikamesra.model;

import java.util.Date;

public class Pengumuman {
    private int pengumumanId;     // <--- Tambah ID untuk identifikasi
    private String judul;
    private String isi;
    private Date tanggal;
    private String ditujukanUntuk; // <--- Tambah field target audiens

    // === Getter & Setter ===
    
    public int getPengumumanId() {
        return pengumumanId;
    }

    public void setPengumumanId(int pengumumanId) {
        this.pengumumanId = pengumumanId;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getDitujukanUntuk() {
        return ditujukanUntuk;
    }

    public void setDitujukanUntuk(String ditujukanUntuk) {
        this.ditujukanUntuk = ditujukanUntuk;
    }
    
}
