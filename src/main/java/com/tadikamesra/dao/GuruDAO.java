package com.tadikamesra.dao;

import com.tadikamesra.model.Guru;
import com.tadikamesra.controller.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuruDAO {

    public List<Guru> getAll() {
        List<Guru> list = new ArrayList<>();
        String sql = "SELECT " +
                     "g.guru_id, g.nama, g.nip, " +
                     "u.username, " +
                     "mp.nama_mapel, " +
                     "k.nama_kelas " +
                     "FROM guru g " +
                     "LEFT JOIN users u ON g.user_id = u.user_id " +
                     "LEFT JOIN mata_pelajaran mp ON g.mapel_id = mp.mapel_id " +
                     "LEFT JOIN kelas k ON g.guru_id = k.wali_kelas_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Guru guru = new Guru();
                guru.setId(rs.getInt("guru_id"));
                guru.setNama(rs.getString("nama"));
                guru.setNip(rs.getString("nip"));
                guru.setUsername(rs.getString("username"));
                guru.setMataPelajaran(rs.getString("nama_mapel"));
                guru.setWaliKelas(rs.getString("nama_kelas"));
                list.add(guru);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<String> getAllNamaMapel() {
    List<String> list = new ArrayList<>();
    String sql = "SELECT nama_mapel FROM mata_pelajaran";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            list.add(rs.getString("nama_mapel"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}


    // Tambahkan insert, update, delete, dan isNipUnique nanti jika perlu
}
