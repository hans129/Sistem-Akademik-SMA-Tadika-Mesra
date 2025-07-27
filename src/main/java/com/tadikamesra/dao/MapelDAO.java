package com.tadikamesra.dao;

import com.tadikamesra.model.Mapel;
import com.tadikamesra.controller.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MapelDAO {

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

    // Tambahkan method lain jika perlu (insert, update, delete)
}
