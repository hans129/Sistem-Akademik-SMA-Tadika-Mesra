package com.tadikamesra.model;

public class Guru {
    private int guruId;
    private String nama;
    private String nip;
    private int userId;

    // Constructor kosong
    public Guru() {
    }

    // Getter dan Setter
    public int getGuruId() {
        return guruId;
    }

    public void setGuruId(int guruId) {
        this.guruId = guruId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
