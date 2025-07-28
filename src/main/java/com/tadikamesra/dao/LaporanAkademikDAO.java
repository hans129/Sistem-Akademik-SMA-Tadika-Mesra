package com.tadikamesra.dao;

import java.sql.*;
import java.util.*;
import com.tadikamesra.model.LaporanAkademik;
import com.tadikamesra.controller.DBConnection;

public class LaporanAkademikDAO {

    public List<LaporanAkademik> getLaporanByFilter(String kelasId, String siswaId, String semester) {
        List<LaporanAkademik> list = new ArrayList<>();
        String sql = "SELECT la.id, la.siswa_id, s.nama AS nama_siswa, m.nama AS nama_mapel, " +
                     "k.nama AS nama_kelas, la.absen, la.tugas, la.quiz, la.uts, la.uas, la.nilai, la.grade " +
                     "FROM laporan_akademik la " +
                     "JOIN siswa s ON la.siswa_id = s.id " +
                     "JOIN mata_pelajaran m ON la.mapel_id = m.id " +
                     "JOIN kelas k ON la.kelas_id = k.id " +
                     "WHERE la.semester = ? AND la.kelas_id = ? AND la.siswa_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, semester);
            stmt.setString(2, kelasId);
            stmt.setString(3, siswaId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new LaporanAkademik(
                    rs.getInt("id"),
                    rs.getInt("siswa_id"),
                    rs.getString("nama_siswa"),
                    rs.getString("nama_mapel"),
                    rs.getString("nama_kelas"),
                    rs.getInt("absen"),
                    rs.getInt("tugas"),
                    rs.getInt("quiz"),
                    rs.getInt("uts"),
                    rs.getInt("uas"),
                    rs.getInt("nilai"),
                    rs.getString("grade")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
