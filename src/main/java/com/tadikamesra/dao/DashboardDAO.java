package com.tadikamesra.dao;

import com.tadikamesra.model.Pengumuman;
import com.tadikamesra.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {
    private Connection conn;

    // Buat dua constructor: default dan yang pakai koneksi dari luar
    public DashboardDAO() throws SQLException {
        this.conn = DBConnection.getConnection();
    }

    public DashboardDAO(Connection conn) {
        this.conn = conn;
    }

    public int getTotalSiswa() {
        String sql = "SELECT COUNT(*) FROM siswa";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalGuru() {
        String sql = "SELECT COUNT(*) FROM guru";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalMapel() {
        String sql = "SELECT COUNT(*) FROM mapel";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalPengumumanAktif() {
        String sql = "SELECT COUNT(*) FROM pengumuman";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalJadwalMingguIni() {
        String sql = "SELECT COUNT(*) FROM jadwal WHERE WEEK(tanggal) = WEEK(CURRENT_DATE()) AND YEAR(tanggal) = YEAR(CURRENT_DATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Pengumuman> getPengumumanTerbaru() {
        List<Pengumuman> list = new ArrayList<>();
        String sql = "SELECT * FROM pengumuman ORDER BY tanggal DESC LIMIT 5";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pengumuman p = new Pengumuman();
                p.setJudul(rs.getString("judul"));
                p.setIsi(rs.getString("isi"));
                p.setTanggal(rs.getDate("tanggal"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
