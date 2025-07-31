package com.tadikamesra.dao;

import com.tadikamesra.model.Pengumuman;
import com.tadikamesra.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PengumumanDAO {

    // Ambil pengumuman terbaru (tanpa filter peran)
    public static List<Pengumuman> getRecentPengumuman() {
        List<Pengumuman> list = new ArrayList<>();
        String sql = "SELECT isi FROM pengumuman ORDER BY tanggal DESC LIMIT 10";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pengumuman p = new Pengumuman();
                p.setIsi(rs.getString("isi"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Ambil pengumuman terbaru khusus untuk peran tertentu (guru/siswa/semua)
public static List<Pengumuman> getRecentPengumumanFor(String ditujukanUntuk) {
    List<Pengumuman> list = new ArrayList<>();
    String sql = "SELECT * FROM pengumuman WHERE ditujukan_untuk = ? OR LOWER(ditujukan_untuk) = 'semua' ORDER BY tanggal DESC LIMIT 10";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, ditujukanUntuk.toLowerCase()); // Biar case-insensitive

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pengumuman p = new Pengumuman();
                p.setPengumumanId(rs.getInt("pengumuman_id"));
                p.setJudul(rs.getString("judul"));
                p.setIsi(rs.getString("isi"));
                p.setTanggal(rs.getDate("tanggal"));
                p.setDitujukanUntuk(rs.getString("ditujukan_untuk"));
                list.add(p);
            }
        }

    } catch (SQLException e) {
        System.err.println("Gagal ambil pengumuman: " + e.getMessage());
        e.printStackTrace(); // Ganti logger di deploy
    }

    return list;
}


    // Tambah pengumuman
    public void insert(Pengumuman p) {
        String sql = "INSERT INTO pengumuman (judul, isi, tanggal, ditujukan_untuk) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getJudul());
            ps.setString(2, p.getIsi());
            ps.setDate(3, new java.sql.Date(p.getTanggal().getTime()));
            ps.setString(4, p.getDitujukanUntuk());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update pengumuman
    public void update(Pengumuman p) {
        String sql = "UPDATE pengumuman SET judul = ?, isi = ?, tanggal = ?, ditujukan_untuk = ? WHERE pengumuman_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getJudul());
            ps.setString(2, p.getIsi());
            ps.setDate(3, new java.sql.Date(p.getTanggal().getTime()));
            ps.setString(4, p.getDitujukanUntuk());
            ps.setInt(5, p.getPengumumanId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hapus pengumuman
    public void delete(int id) {
        String sql = "DELETE FROM pengumuman WHERE pengumuman_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Ambil semua pengumuman (untuk admin/tabel utama)
    public static List<Pengumuman> getAll() {
        List<Pengumuman> list = new ArrayList<>();
        String sql = "SELECT * FROM pengumuman ORDER BY tanggal DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pengumuman p = new Pengumuman();
                p.setPengumumanId(rs.getInt("pengumuman_id"));
                p.setJudul(rs.getString("judul"));
                p.setIsi(rs.getString("isi"));
                p.setTanggal(rs.getDate("tanggal"));
                p.setDitujukanUntuk(rs.getString("ditujukan_untuk"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Ambil satu pengumuman by ID
    public Pengumuman getById(int id) {
        Pengumuman p = null;
        String sql = "SELECT * FROM pengumuman WHERE pengumuman_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Pengumuman();
                    p.setPengumumanId(rs.getInt("pengumuman_id"));
                    p.setJudul(rs.getString("judul"));
                    p.setIsi(rs.getString("isi"));
                    p.setTanggal(rs.getDate("tanggal"));
                    p.setDitujukanUntuk(rs.getString("ditujukan_untuk"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
}
