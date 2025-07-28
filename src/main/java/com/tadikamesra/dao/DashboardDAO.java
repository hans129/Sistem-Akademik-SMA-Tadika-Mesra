package com.tadikamesra.dao;

import com.tadikamesra.util.DBConnection;
import com.tadikamesra.model.Pengumuman;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {
    private Connection conn;

    public DashboardDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public int getTotalSiswa() {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM siswa");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int getTotalGuru() {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM guru");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int getTotalPengumumanAktif() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM pengumuman";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }


    public int getTotalJadwalMingguIni() {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM jadwal WHERE WEEK(tanggal) = WEEK(CURRENT_DATE()) AND YEAR(tanggal) = YEAR(CURRENT_DATE())"
            );
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

 public List<Pengumuman> getPengumumanTerbaru() throws SQLException {
    List<Pengumuman> list = new ArrayList<>();
    String sql = "SELECT * FROM pengumuman ORDER BY tanggal DESC LIMIT 5";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Pengumuman p = new Pengumuman();
            p.setJudul(rs.getString("judul"));
            p.setIsi(rs.getString("isi"));
            p.setTanggal(rs.getDate("tanggal"));
            list.add(p);
        }
    }
    return list;
}
}
