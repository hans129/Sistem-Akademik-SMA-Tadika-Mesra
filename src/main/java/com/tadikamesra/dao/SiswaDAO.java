package com.tadikamesra.dao;

import com.tadikamesra.controller.DBConnection;
import com.tadikamesra.model.Siswa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiswaDAO {

    // Ambil semua data siswa (dengan nama kelas hasil JOIN)
    public List<Siswa> getAll() {
        List<Siswa> list = new ArrayList<>();
        String sql = "SELECT s.*, k.nama_kelas FROM siswa s JOIN kelas k ON s.kelas_id = k.kelas_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Siswa siswa = new Siswa();
                siswa.setSiswaId(rs.getInt("siswa_id"));
                siswa.setNama(rs.getString("nama"));
                siswa.setNis(rs.getString("nis"));
                siswa.setUserId(rs.getInt("user_id"));
                siswa.setKelasId(rs.getInt("kelas_id"));
                siswa.setNamaKelas(rs.getString("nama_kelas")); // hasil join
                list.add(siswa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tambah data siswa
    public void insert(Siswa siswa) {
        String sql = "INSERT INTO siswa (nama, nis, kelas_id, user_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, siswa.getNama());
            stmt.setString(2, siswa.getNis());
            stmt.setInt(3, siswa.getKelasId());
            stmt.setInt(4, siswa.getUserId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update data siswa
    public void update(Siswa siswa) {
        String sql = "UPDATE siswa SET nama=?, nis=?, user_id=?, kelas_id=? WHERE siswa_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, siswa.getNama());
            stmt.setString(2, siswa.getNis());
            stmt.setInt(3, siswa.getUserId());
            stmt.setInt(4, siswa.getKelasId());
            stmt.setInt(5, siswa.getSiswaId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hapus data siswa berdasarkan ID
    public void delete(int siswaId) {
        String sql = "DELETE FROM siswa WHERE siswa_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, siswaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ambil data siswa berdasarkan ID
    public Siswa getById(int id) {
        Siswa siswa = null;
        String sql = "SELECT * FROM siswa WHERE siswa_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                siswa = new Siswa();
                siswa.setSiswaId(rs.getInt("siswa_id"));
                siswa.setNama(rs.getString("nama"));
                siswa.setNis(rs.getString("nis"));
                siswa.setUserId(rs.getInt("user_id"));
                siswa.setKelasId(rs.getInt("kelas_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return siswa;
    }
}
