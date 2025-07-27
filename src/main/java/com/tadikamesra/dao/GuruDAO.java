package com.tadikamesra.dao;

import com.tadikamesra.controller.DBConnection;
import com.tadikamesra.model.Guru;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuruDAO {

    public List<Guru> getAll() {
        List<Guru> list = new ArrayList<>();
        String sql = "SELECT * FROM guru";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Guru guru = new Guru();
                guru.setGuruId(rs.getInt("guru_id"));
                guru.setNama(rs.getString("nama"));
                guru.setNip(rs.getString("nip"));
                guru.setUserId(rs.getInt("user_id")); // Pastikan ini ada!
                list.add(guru);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Guru guru) {
        String sql = "INSERT INTO guru (nama, nip, user_id) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, guru.getNama());
            stmt.setString(2, guru.getNip());
            stmt.setInt(3, guru.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int guruId) {
        String sql = "DELETE FROM guru WHERE guru_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, guruId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tambahkan update() kalau nanti edit guru
}
