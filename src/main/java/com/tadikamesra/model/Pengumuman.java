package com.tadikamesra.model;

import java.util.Date;

public class Pengumuman {
    private String judul;
    private String isi;
    private Date tanggal;

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getIsi() { return isi; }
    public void setIsi(String isi) { this.isi = isi; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
}
