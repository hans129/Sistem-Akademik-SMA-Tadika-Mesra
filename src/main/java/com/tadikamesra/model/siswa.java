
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tadikamesra.model;

/**
 *
 * @author hp
 */
public class siswa {
    private int idSiswa;
    private String nama;
    private String nis;
    private String username;
    private String password;
    private String kelas;

    public siswa() {}

    public siswa(int idSiswa, String nama, String nis, String username, String password, String kelas) {
        this.idSiswa = idSiswa;
        this.nama = nama;
        this.nis = nis;
        this.username = username;
        this.password = password;
        this.kelas = kelas;
    }

    public int getIdSiswa() {
        return idSiswa;
    }

    public void setIdSiswa(int idSiswa) {
        this.idSiswa = idSiswa;
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

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
}