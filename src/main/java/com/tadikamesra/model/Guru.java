package com.tadikamesra.model;

public class Guru {
    private int guruId;
    private int userId;
    private String nama;
    private String nip;
    private String hp;
    private String email;
    private String pendidikan;
    private String jabatan;
    private String foto;
    
    private int mapelId;
    private int waliKelasId;

    private String mataPelajaran; // dari tabel relasi
    private String namaWaliKelas; // bisa dari join kelas
    private String namaMapel;
    
    private String username;
    private String password;
    private int id; // jika kamu pakai guru.setId(...) di servlet
    private String waliKelas; // jika kamu pakai guru.setWaliKelas(...) di servlet


    // Getters & Setters
    public int getGuruId() { return guruId; }
    public void setGuruId(int guruId) { this.guruId = guruId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getId() {
    return id;
    }

    public void setId(int id) {
    this.id = id;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNip() { return nip; }
    public void setNip(String nip) { this.nip = nip; }

    public String getHp() { return hp; }
    public void setHp(String hp) { this.hp = hp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPendidikan() { return pendidikan; }
    public void setPendidikan(String pendidikan) { this.pendidikan = pendidikan; }

    public String getJabatan() { return jabatan; }
    public void setJabatan(String jabatan) { this.jabatan = jabatan; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public int getMapelId() { return mapelId; }
    public void setMapelId(int mapelId) { this.mapelId = mapelId; }

    public int getWaliKelasId() { return waliKelasId; }
    public void setWaliKelasId(int waliKelasId) { this.waliKelasId = waliKelasId; }

    public String getMataPelajaran() { return mataPelajaran; }
    public void setMataPelajaran(String mataPelajaran) { this.mataPelajaran = mataPelajaran; }

    public String getNamaWaliKelas() { return namaWaliKelas; }
    public void setNamaWaliKelas(String namaWaliKelas) { this.namaWaliKelas = namaWaliKelas; }
    
    public String getUsername() {
    return username;
}
public void setUsername(String username) {
    this.username = username;
}

public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}

public String getWaliKelas() {
    return waliKelas;
}
public void setWaliKelas(String waliKelas) {
    this.waliKelas = waliKelas;
}

public String getNamaMapel() {
    return namaMapel;
}

public void setNamaMapel(String namaMapel) {
    this.namaMapel = namaMapel;
}

}
