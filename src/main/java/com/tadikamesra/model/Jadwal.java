package com.tadikamesra.model;

public class Jadwal {
    private int jadwalId;
    private String hari;
    private String jamMulai;
    private String jamSelesai;
    private String namaKelas; // TAMBAHKAN INI
    private String namaMapel;
    private String namaGuru;
    private int kelasId;

    public Jadwal() {
    }

    public Jadwal(int jadwalId, String hari, String jamMulai, String jamSelesai,
                  String namaMapel, String namaGuru, int kelasId) {
        this.jadwalId = jadwalId;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.namaMapel = namaMapel;
        this.namaGuru = namaGuru;
        this.kelasId = kelasId;
    }

    public int getJadwalId() { return jadwalId; }
    public void setJadwalId(int jadwalId) { this.jadwalId = jadwalId; }

    public String getHari() { return hari; }
    public void setHari(String hari) { this.hari = hari; }

    public String getJamMulai() { return jamMulai; }
    public void setJamMulai(String jamMulai) { this.jamMulai = jamMulai; }

    public String getJamSelesai() { return jamSelesai; }
    public void setJamSelesai(String jamSelesai) { this.jamSelesai = jamSelesai; }

    public String getNamaMapel() { return namaMapel; }
    public void setNamaMapel(String namaMapel) { this.namaMapel = namaMapel; }

    public String getNamaGuru() { return namaGuru; }
    public void setNamaGuru(String namaGuru) { this.namaGuru = namaGuru; }

    public int getKelasId() { return kelasId; }
    public void setKelasId(int kelasId) { this.kelasId = kelasId; }
    
    public String getNamaKelas() {
    return namaKelas;
}

    public void setNamaKelas(String namaKelas) {
    this.namaKelas = namaKelas;
}
}
