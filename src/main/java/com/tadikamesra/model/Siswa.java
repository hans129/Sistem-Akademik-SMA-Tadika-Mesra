package com.tadikamesra.model;

public class Siswa {
    private int siswaId;
    private int userId;
    private String nama;
    private String nisn;
    private String noHp;
    private int semester;
    private String email;
    private int kelasId;
    private String kelas;
    private String waliKelas;
    private String emailWali;

    // Data Pribadi Tambahan
    private String tempatLahir;
    private String tanggalLahir;
    private String agama;
    private String jenisKelamin;
    private String statusPernikahan;
    private int jumlahSaudara;
    private int anakKe;
    private String kewarganegaraan;
    private String noKK;
    private String tipeIdentitas;
    private String nomorIdentitas;
    private String telpRumah;

    // Alamat KTP
    private String alamatKtp;
    private String jalanKtp;
    private String provinsiKtp;
    private String kotaKtp;
    private String kecamatanKtp;
    private String kelurahanKtp;
    private int rtKtp;
    private int rwKtp;
    private String kodeposKtp;

    // Alamat Domisili
    private String alamatDomisili;
    private String jalanDomisili;
    private String provinsiDomisili;
    private String kotaDomisili;
    private String kecamatanDomisili;
    private String kelurahanDomisili;
    private int rtDomisili;
    private int rwDomisili;
    private String kodeposDomisili;
    private String statusTinggal;

    private String foto;

    // --- Getter & Setter ---
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

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getKelasId() { return kelasId; }
    public void setKelasId(int kelasId) { this.kelasId = kelasId; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public String getWaliKelas() { return waliKelas; }
    public void setWaliKelas(String waliKelas) { this.waliKelas = waliKelas; }

    public String getEmailWali() { return emailWali; }
    public void setEmailWali(String emailWali) { this.emailWali = emailWali; }

    public String getTempatLahir() { return tempatLahir; }
    public void setTempatLahir(String tempatLahir) { this.tempatLahir = tempatLahir; }

    public String getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(String tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public String getAgama() { return agama; }
    public void setAgama(String agama) { this.agama = agama; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getStatusPernikahan() { return statusPernikahan; }
    public void setStatusPernikahan(String statusPernikahan) { this.statusPernikahan = statusPernikahan; }

    public int getJumlahSaudara() { return jumlahSaudara; }
    public void setJumlahSaudara(int jumlahSaudara) { this.jumlahSaudara = jumlahSaudara; }

    public int getAnakKe() { return anakKe; }
    public void setAnakKe(int anakKe) { this.anakKe = anakKe; }

    public String getKewarganegaraan() { return kewarganegaraan; }
    public void setKewarganegaraan(String kewarganegaraan) { this.kewarganegaraan = kewarganegaraan; }

    public String getNoKK() { return noKK; }
    public void setNoKK(String noKK) { this.noKK = noKK; }

    public String getTipeIdentitas() { return tipeIdentitas; }
    public void setTipeIdentitas(String tipeIdentitas) { this.tipeIdentitas = tipeIdentitas; }

    public String getNomorIdentitas() { return nomorIdentitas; }
    public void setNomorIdentitas(String nomorIdentitas) { this.nomorIdentitas = nomorIdentitas; }

    public String getTelpRumah() { return telpRumah; }
    public void setTelpRumah(String telpRumah) { this.telpRumah = telpRumah; }

    public String getAlamatKtp() { return alamatKtp; }
    public void setAlamatKtp(String alamatKtp) { this.alamatKtp = alamatKtp; }

    public String getJalanKtp() { return jalanKtp; }
    public void setJalanKtp(String jalanKtp) { this.jalanKtp = jalanKtp; }

    public String getProvinsiKtp() { return provinsiKtp; }
    public void setProvinsiKtp(String provinsiKtp) { this.provinsiKtp = provinsiKtp; }

    public String getKotaKtp() { return kotaKtp; }
    public void setKotaKtp(String kotaKtp) { this.kotaKtp = kotaKtp; }

    public String getKecamatanKtp() { return kecamatanKtp; }
    public void setKecamatanKtp(String kecamatanKtp) { this.kecamatanKtp = kecamatanKtp; }

    public String getKelurahanKtp() { return kelurahanKtp; }
    public void setKelurahanKtp(String kelurahanKtp) { this.kelurahanKtp = kelurahanKtp; }

    public int getRtKtp() { return rtKtp; }
    public void setRtKtp(int rtKtp) { this.rtKtp = rtKtp; }

    public int getRwKtp() { return rwKtp; }
    public void setRwKtp(int rwKtp) { this.rwKtp = rwKtp; }

    public String getKodeposKtp() { return kodeposKtp; }
    public void setKodeposKtp(String kodeposKtp) { this.kodeposKtp = kodeposKtp; }

    public String getAlamatDomisili() { return alamatDomisili; }
    public void setAlamatDomisili(String alamatDomisili) { this.alamatDomisili = alamatDomisili; }

    public String getJalanDomisili() { return jalanDomisili; }
    public void setJalanDomisili(String jalanDomisili) { this.jalanDomisili = jalanDomisili; }

    public String getProvinsiDomisili() { return provinsiDomisili; }
    public void setProvinsiDomisili(String provinsiDomisili) { this.provinsiDomisili = provinsiDomisili; }

    public String getKotaDomisili() { return kotaDomisili; }
    public void setKotaDomisili(String kotaDomisili) { this.kotaDomisili = kotaDomisili; }

    public String getKecamatanDomisili() { return kecamatanDomisili; }
    public void setKecamatanDomisili(String kecamatanDomisili) { this.kecamatanDomisili = kecamatanDomisili; }

    public String getKelurahanDomisili() { return kelurahanDomisili; }
    public void setKelurahanDomisili(String kelurahanDomisili) { this.kelurahanDomisili = kelurahanDomisili; }

    public int getRtDomisili() { return rtDomisili; }
    public void setRtDomisili(int rtDomisili) { this.rtDomisili = rtDomisili; }

    public int getRwDomisili() { return rwDomisili; }
    public void setRwDomisili(int rwDomisili) { this.rwDomisili = rwDomisili; }

    public String getKodeposDomisili() { return kodeposDomisili; }
    public void setKodeposDomisili(String kodeposDomisili) { this.kodeposDomisili = kodeposDomisili; }

    public String getStatusTinggal() { return statusTinggal; }
    public void setStatusTinggal(String statusTinggal) { this.statusTinggal = statusTinggal; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}
