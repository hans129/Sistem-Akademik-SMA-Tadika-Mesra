package com.tadikamesra.dao;

import com.tadikamesra.model.Jadwal;
import java.sql.*;
import java.util.*;

public class JadwalDAO {
    private Connection connection;

    public JadwalDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Jadwal jadwal) throws SQLException {
        String sql = "INSERT INTO jadwal (nama_kegiatan, waktu, jenis_kegiatan, kelas_id, tanggal, pengampu_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, jadwal.getNamaKegiatan());
            stmt.setString(2, jadwal.getWaktu());
            stmt.setString(3, jadwal.getJenisKegiatan());
            stmt.setInt(4, jadwal.getKelasId());
            stmt.setString(5,jadwal.getTanggal());
            stmt.setInt(6, jadwal.getPengampuId());
            stmt.executeUpdate();
        }
    }

    public List<Jadwal> getAll() throws SQLException {
        List<Jadwal> list = new ArrayList<>();
        String sql = "SELECT * FROM jadwal";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Jadwal j = new Jadwal(
                    rs.getInt("id"),
                    rs.getString("nama_kegiatan"),
                    rs.getString("waktu"),
                    rs.getString("jenis_kegiatan"),
                    rs.getInt("kelas_id"),
                    rs.getDate("tanggal").toString(),
                    rs.getInt("pengampu_id")
                );
                list.add(j);
            }
        }
        return list;
    }
}
