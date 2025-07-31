package com.tadikamesra.model;

public class Siswa {
    private int siswaId;
    private int userId;
    private String nama;
    private String nisn;
    private String jenisKelamin;
    private String alamat;
    private String noHp;
    private String email;
    private String kelas;
    private int kelasId;
    private String namaKelas;
    private int semester;
    private String waliKelas;
    private String emailWali;
    private String foto; // ✅ Tambahan field foto

    // Getters & Setters
    public int getSiswaId() { return siswaId; }
    public void setSiswaId(int siswaId) { this.siswaId = siswaId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNisn() { return nisn; }
    public void setNisn(String nisn) { this.nisn = nisn; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public int getKelasId() { return kelasId; }
    public void setKelasId(int kelasId) { this.kelasId = kelasId; }

    public String getNamaKelas() { return namaKelas; }
    public void setNamaKelas(String namaKelas) { this.namaKelas = namaKelas; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getWaliKelas() { return waliKelas; }
    public void setWaliKelas(String waliKelas) { this.waliKelas = waliKelas; }

    public String getEmailWali() { return emailWali; }
    public void setEmailWali(String emailWali) { this.emailWali = emailWali; }

    public String getFoto() { return foto; }             // ✅ Getter untuk foto
    public void setFoto(String foto) { this.foto = foto; } // ✅ Setter untuk foto
}
