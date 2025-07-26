package com.tadikamesra.dao;

import com.tadikamesra.controller.DBConnection;
import com.tadikamesra.model.Guru;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuruDAO {

    public List<Guru> getAllGuru() {
        List<Guru> list = new ArrayList<>();
        String sql = "SELECT * FROM guru";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Guru g = new Guru();
                g.setId(rs.getInt("id"));
                g.setNama(rs.getString("nama"));
                g.setNip(rs.getString("nip"));
                g.setUsername(rs.getString("username"));
                g.setPassword(rs.getString("password"));
                g.setMataPelajaran(rs.getString("mata_pelajaran"));
                g.setWaliKelas(rs.getString("wali_kelas"));
                list.add(g);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Guru> getAll() {
    List<Guru> list = new ArrayList<>();
    String sql = "SELECT * FROM guru";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Guru g = new Guru();
            g.setId(rs.getInt("id_guru"));
            g.setNama(rs.getString("nama"));
            g.setNip(rs.getString("nip"));
            g.setUsername(rs.getString("username"));
            g.setMataPelajaran(rs.getString("mata_pelajaran"));
            g.setWaliKelas(rs.getString("wali_kelas"));
            list.add(g);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
   

    public void insert(Guru g) {
        String sql = "INSERT INTO guru (nama, nip, username, password, mata_pelajaran, wali_kelas) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, g.getNama());
            stmt.setString(2, g.getNip());
            stmt.setString(3, g.getUsername());
            stmt.setString(4, g.getPassword());
            stmt.setString(5, g.getMataPelajaran());
            stmt.setString(6, g.getWaliKelas());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Guru g) {
        boolean updatePassword = g.getPassword() != null && !g.getPassword().trim().isEmpty();
        String sql;

        if (updatePassword) {
            sql = "UPDATE guru SET nama=?, nip=?, username=?, password=?, mata_pelajaran=?, wali_kelas=? WHERE id=?";
        } else {
            sql = "UPDATE guru SET nama=?, nip=?, username=?, mata_pelajaran=?, wali_kelas=? WHERE id=?";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, g.getNama());
            stmt.setString(2, g.getNip());
            stmt.setString(3, g.getUsername());

            if (updatePassword) {
                stmt.setString(4, g.getPassword());
                stmt.setString(5, g.getMataPelajaran());
                stmt.setString(6, g.getWaliKelas());
                stmt.setInt(7, g.getId());
            } else {
                stmt.setString(4, g.getMataPelajaran());
                stmt.setString(5, g.getWaliKelas());
                stmt.setInt(6, g.getId());
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM guru WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
