package com.tadikamesra.model;

public class Siswa {
    private int siswaId;
    private String nama;
    private String nis;
    private Integer userId;
    private Integer kelasId;
    private String namaKelas;

    // ✅ Constructor kosong (untuk DAO)
    public Siswa() {
    }

    // ✅ Constructor isi (untuk insert cepat)
        public Siswa(String nama, String nis, int kelasId, int userId) {
           this.nama = nama;
           this.nis = nis;
           this.kelasId = kelasId;
           this.userId = userId;
       }


    // Getter dan Setter
    public int getSiswaId() {
        return siswaId;
    }

    public void setSiswaId(int siswaId) {
        this.siswaId = siswaId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getKelasId() {
        return kelasId;
    }

    public void setKelasId(Integer kelasId) {
        this.kelasId = kelasId;
    }

    // Tambahan untuk nama kelas (hasil join)
    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }
}
