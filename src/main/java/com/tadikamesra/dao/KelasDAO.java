package com.tadikamesra.dao;

import com.tadikamesra.model.Kelas;
import com.tadikamesra.controller.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KelasDAO {

    public List<Kelas> getAll() {
        List<Kelas> list = new ArrayList<>();
        String sql = "SELECT * FROM kelas";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Kelas kelas = new Kelas();
                kelas.setKelasId(rs.getInt("kelas_id"));
                kelas.setNamaKelas(rs.getString("nama_kelas"));
                kelas.setWaliKelasId(rs.getInt("wali_kelas_id"));
                list.add(kelas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tambahkan method lain jika perlu
}
