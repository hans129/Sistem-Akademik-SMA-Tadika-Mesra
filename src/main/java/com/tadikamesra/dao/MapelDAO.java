package com.tadikamesra.dao;

import com.tadikamesra.controller.DBConnection;
import com.tadikamesra.model.Mapel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MapelDAO {

    // Ambil semua data mata pelajaran
    public List<Mapel> getAll() {
        List<Mapel> list = new ArrayList<>();
        String sql = "SELECT * FROM mata_pelajaran";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mapel mapel = new Mapel();
                mapel.setMapelId(rs.getInt("mapel_id"));
                mapel.setNamaMapel(rs.getString("nama_mapel"));
                mapel.setGuruId(rs.getInt("guru_id"));
                list.add(mapel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tambah mata pelajaran baru
    public void insert(Mapel mapel) {
        String sql = "INSERT INTO mata_pelajaran (nama_mapel, guru_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mapel.getNamaMapel());
            stmt.setInt(2, mapel.getGuruId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update data mata pelajaran
    public void update(Mapel mapel) {
        String sql = "UPDATE mata_pelajaran SET nama_mapel = ?, guru_id = ? WHERE mapel_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mapel.getNamaMapel());
            stmt.setInt(2, mapel.getGuruId());
            stmt.setInt(3, mapel.getMapelId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hapus mata pelajaran berdasarkan ID
    public void delete(int mapelId) {
        String sql = "DELETE FROM mata_pelajaran WHERE mapel_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mapelId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ambil satu data Mapel berdasarkan ID
    public Mapel getById(int mapelId) {
        String sql = "SELECT * FROM mata_pelajaran WHERE mapel_id = ?";
        Mapel mapel = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mapelId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mapel = new Mapel();
                mapel.setMapelId(rs.getInt("mapel_id"));
                mapel.setNamaMapel(rs.getString("nama_mapel"));
                mapel.setGuruId(rs.getInt("guru_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapel;
    }
}
