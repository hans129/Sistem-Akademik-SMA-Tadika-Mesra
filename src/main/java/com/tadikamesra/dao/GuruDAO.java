package com.tadikamesra.dao;

import com.tadikamesra.model.Guru;
import java.sql.*;
import java.util.*;

public class GuruDAO {
    private Connection conn;

    public GuruDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Guru> getAll() throws SQLException {
        List<Guru> list = new ArrayList<>();
        String sql = "SELECT * FROM guru";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Guru g = new Guru();
                g.setIdGuru(rs.getInt("id_guru"));
                g.setNama(rs.getString("nama"));
                g.setUsername(rs.getString("username"));
                g.setPassword(rs.getString("password"));
                g.setMataPelajaran(rs.getString("mata_pelajaran"));
                g.setWaliKelas(rs.getString("wali_kelas"));
                list.add(g);
            }
        }
        return list;
    }

    public void insert(Guru g) throws SQLException {
        String sql = "INSERT INTO guru (nama, username, password, mata_pelajaran, wali_kelas) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, g.getNama());
            stmt.setString(2, g.getUsername());
            stmt.setString(3, g.getPassword());
            stmt.setString(4, g.getMataPelajaran());
            stmt.setString(5, g.getWaliKelas());
            stmt.executeUpdate();
        }
    }
}
