package com.tadikamesra.model;

public class Kelas {
    private int kelasId;
    private String namaKelas;
    private String tingkat; // opsional, misal "X", "XI", "XII"
    private String jurusan; // opsional, misal "IPA", "IPS"

    // Getter & Setter
    public int getKelasId() {
        return kelasId;
    }

    public void setKelasId(int kelasId) {
        this.kelasId = kelasId;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}
