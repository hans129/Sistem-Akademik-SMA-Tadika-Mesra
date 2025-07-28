package com.tadikamesra.model;

public class Jadwal {
    private int id;
    private String namaKegiatan;
    private String waktu;
    private String jenisKegiatan;
    private int kelasId;
    private String tanggal;
    private int pengampuId;

    public Jadwal() {}

    public Jadwal(String namaKegiatan, String waktu, String jenisKegiatan, int kelasId, String tanggal, int pengampuId) {
        this.namaKegiatan = namaKegiatan;
        this.waktu = waktu;
        this.jenisKegiatan = jenisKegiatan;
        this.kelasId = kelasId;
        this.tanggal = tanggal;
        this.pengampuId = pengampuId;
    }

    public Jadwal(int id, String namaKegiatan, String waktu, String jenisKegiatan, int kelasId, String tanggal, int pengampuId) {
        this(namaKegiatan, waktu, jenisKegiatan, kelasId, tanggal, pengampuId);
        this.id = id;
    }

    public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public String getNamaKegiatan() {
    return namaKegiatan;
}

public void setNamaKegiatan(String namaKegiatan) {
    this.namaKegiatan = namaKegiatan;
}

public String getWaktu() {
    return waktu;
}

public void setWaktu(String waktu) {
    this.waktu = waktu;
}

public String getJenisKegiatan() {
    return jenisKegiatan;
}

public void setJenisKegiatan(String jenisKegiatan) {
    this.jenisKegiatan = jenisKegiatan;
}

public int getKelasId() {
    return kelasId;
}

public void setKelasId(int kelasId) {
    this.kelasId = kelasId;
}

public String getTanggal() {
    return tanggal;
}

public void setTanggal(String tanggal) {
    this.tanggal = tanggal;
}

public int getPengampuId() {
    return pengampuId;
}

public void setPengampuId(int pengampuId) {
    this.pengampuId = pengampuId;
}

// Getter & Setter
    // ... (lengkapi semua getter/setter seperti pola pada model Guru)
}
