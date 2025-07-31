package com.tadikamesra.dao;

import com.tadikamesra.model.Jadwal;
import com.tadikamesra.util.DBConnection;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JadwalDAO {

    private static String getNamaHariIndonesia() {
        DayOfWeek hari = LocalDate.now().getDayOfWeek();
        switch (hari) {
            case MONDAY: return "Senin";
            case TUESDAY: return "Selasa";
            case WEDNESDAY: return "Rabu";
            case THURSDAY: return "Kamis";
            case FRIDAY: return "Jumat";
            case SATURDAY: return "Sabtu";
            case SUNDAY: return "Minggu";
            default: return "";
        }
    }

    public static List<Jadwal> getJadwalHariIni(int userId) {
        List<Jadwal> list = new ArrayList<>();
        String hariIni = getNamaHariIndonesia();

        String sql =
            "SELECT j.jadwal_id, j.hari, j.jam_mulai, j.jam_selesai, " +
            "       m.nama_mapel, g.nama AS nama_guru, j.kelas_id " +
            "FROM jadwal j " +
            "JOIN mapel m ON j.mapel_id = m.mapel_id " +
            "JOIN guru g ON j.guru_id = g.guru_id " +
            "JOIN kelas k ON j.kelas_id = k.kelas_id " +
            "JOIN siswa s ON s.kelas_id = k.kelas_id " +
            "WHERE g.user_id = ? AND j.hari = ? " +
            "ORDER BY j.jam_mulai";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, hariIni);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Jadwal jadwal = new Jadwal();
                    jadwal.setJadwalId(rs.getInt("jadwal_id"));
                    jadwal.setHari(rs.getString("hari"));
                    jadwal.setJamMulai(rs.getString("jam_mulai"));
                    jadwal.setJamSelesai(rs.getString("jam_selesai"));
                    jadwal.setNamaMapel(rs.getString("nama_mapel"));
                    jadwal.setNamaGuru(rs.getString("nama_guru"));
                    jadwal.setKelasId(rs.getInt("kelas_id"));
                    list.add(jadwal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Jadwal> getJadwalHariIniGuru(int userId) {
        List<Jadwal> list = new ArrayList<>();
        String hariIni = getNamaHariIndonesia();

        String sql =
            "SELECT j.jadwal_id, j.hari, j.jam_mulai, j.jam_selesai, " +
            "       m.nama_mapel, k.nama_kelas " +
            "FROM jadwal j " +
            "JOIN mata_pelajaran m ON j.mapel_id = m.mapel_id " +
            "JOIN kelas k ON j.kelas_id = k.kelas_id " +
            "JOIN guru g ON j.guru_id = g.guru_id " +
            "WHERE g.user_id = ? AND j.hari = ? " +
            "ORDER BY j.jam_mulai";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, hariIni);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Jadwal jadwal = new Jadwal();
                    jadwal.setJadwalId(rs.getInt("jadwal_id"));
                    jadwal.setHari(rs.getString("hari"));
                    jadwal.setJamMulai(rs.getString("jam_mulai"));
                    jadwal.setJamSelesai(rs.getString("jam_selesai"));
                    jadwal.setNamaMapel(rs.getString("nama_mapel"));
                    jadwal.setNamaKelas(rs.getString("nama_kelas"));
                    list.add(jadwal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Jadwal> getByGuruId(int guruId) {
        List<Jadwal> list = new ArrayList<>();

        String sql =
            "SELECT j.jadwal_id, j.hari, j.jam_mulai, j.jam_selesai, " +
            "       m.nama_mapel, k.nama_kelas " +
            "FROM jadwal j " +
            "JOIN mata_pelajaran m ON j.mapel_id = m.mapel_id " +
            "JOIN kelas k ON j.kelas_id = k.kelas_id " +
            "WHERE j.guru_id = ? " +
            "ORDER BY FIELD(j.hari, 'Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu', 'Minggu'), j.jam_mulai";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, guruId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Jadwal jadwal = new Jadwal();
                    jadwal.setJadwalId(rs.getInt("jadwal_id"));
                    jadwal.setHari(rs.getString("hari"));
                    jadwal.setJamMulai(rs.getString("jam_mulai"));
                    jadwal.setJamSelesai(rs.getString("jam_selesai"));
                    jadwal.setNamaMapel(rs.getString("nama_mapel"));
                    jadwal.setNamaKelas(rs.getString("nama_kelas"));
                    list.add(jadwal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Jadwal> getByKelasId(int kelasId) {
    List<Jadwal> list = new ArrayList<>();

    String sql = "SELECT j.jadwal_id, j.hari, j.jam_mulai, j.jam_selesai, " +
                 "       m.nama_mapel, g.nama AS nama_guru, k.nama_kelas " +
                 "FROM jadwal j " +
                 "JOIN mata_pelajaran m ON j.mapel_id = m.mapel_id " +
                 "JOIN guru g ON j.guru_id = g.guru_id " +
                 "JOIN kelas k ON j.kelas_id = k.kelas_id " +
                 "WHERE j.kelas_id = ? " +
                 "ORDER BY FIELD(j.hari, 'Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu', 'Minggu'), j.jam_mulai";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, kelasId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Jadwal jadwal = new Jadwal();
                jadwal.setJadwalId(rs.getInt("jadwal_id"));
                jadwal.setHari(rs.getString("hari"));
                jadwal.setJamMulai(rs.getString("jam_mulai"));
                jadwal.setJamSelesai(rs.getString("jam_selesai"));
                jadwal.setNamaMapel(rs.getString("nama_mapel"));
                jadwal.setNamaGuru(rs.getString("nama_guru"));
                jadwal.setNamaKelas(rs.getString("nama_kelas"));
                list.add(jadwal);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

}
