package com.tadikamesra.dao;

import com.tadikamesra.model.Siswa;
import com.tadikamesra.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SiswaDAO {

    public static Siswa getSiswaByUserId(int userId) {
        Siswa siswa = null;

        String sql = "SELECT s.siswa_id, s.user_id, s.nama, s.nisn, s.noHp AS no_hp, s.semester, s.email, " +
                     "s.kelas_id, k.nama_kelas AS kelas, g.nama AS wali_kelas, g.email AS email_wali " +
                     "FROM siswa s " +
                     "JOIN kelas k ON s.kelas_id = k.kelas_id " +
                     "LEFT JOIN guru g ON k.wali_kelas_id = g.guru_id " +
                     "WHERE s.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                siswa = new Siswa();
                siswa.setSiswaId(rs.getInt("siswa_id"));
                siswa.setUserId(rs.getInt("user_id"));
                siswa.setNama(rs.getString("nama"));
                siswa.setNisn(rs.getString("nisn"));
                siswa.setNoHp(rs.getString("no_hp"));
                siswa.setSemester(rs.getInt("semester"));
                siswa.setEmail(rs.getString("email"));
                siswa.setKelasId(rs.getInt("kelas_id")); // ✅ tambahkan ini
                siswa.setKelas(rs.getString("kelas"));
                siswa.setWaliKelas(rs.getString("wali_kelas"));
                siswa.setEmailWali(rs.getString("email_wali"));
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return siswa;
    }

}
