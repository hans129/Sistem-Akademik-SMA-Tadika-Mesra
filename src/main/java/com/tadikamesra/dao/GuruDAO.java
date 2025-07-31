package com.tadikamesra.dao;

import com.tadikamesra.model.Guru;
import com.tadikamesra.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuruDAO {

    public static Guru getGuruByUserId(int userId) {
        Guru guru = null;
        String sql = "SELECT g.guru_id, g.user_id, g.nama, g.nip, g.hp, g.email, g.pendidikan, g.jabatan, g.foto, " +
             "g.mapel_id, m.nama_mapel AS mata_pelajaran, " + // ganti alias
             "g.wali_kelas_id, k.nama_kelas AS nama_wali_kelas " +
             "FROM guru g " +
             "LEFT JOIN mata_pelajaran m ON g.mapel_id = m.mapel_id " +
             "LEFT JOIN kelas k ON g.wali_kelas_id = k.kelas_id " +
             "WHERE g.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                guru = extractGuru(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guru;
    }

    public static Guru getById(int id) {
        Guru guru = null;
        String sql = "SELECT * FROM guru WHERE guru_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                guru = extractGuru(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guru;
    }

    public static List<Guru> getAll() {
        List<Guru> list = new ArrayList<>();
        String sql = "SELECT * FROM guru";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractGuru(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getAllNamaMapel() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT nama_mapel FROM mapel";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("nama_mapel"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getAllNamaKelas() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT nama_kelas FROM kelas";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("nama_kelas"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

// INSERT GURU
public static void insert(Guru guru) throws Exception {
    String sql = "INSERT INTO guru (nama, nip, mapel_id, wali_kelas_id, email, hp, pendidikan, jabatan, foto, user_id) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, guru.getNama());
        stmt.setString(2, guru.getNip());
        stmt.setInt(3, guru.getMapelId());
        stmt.setInt(4, guru.getWaliKelasId());
        stmt.setString(5, guru.getEmail());
        stmt.setString(6, guru.getHp());
        stmt.setString(7, guru.getPendidikan());
        stmt.setString(8, guru.getJabatan());
        stmt.setString(9, guru.getFoto());
        stmt.setInt(10, guru.getUserId());

        stmt.executeUpdate();
    }
}

// UPDATE GURU
public static void update(Guru guru) throws Exception {
    String sql = "UPDATE guru SET nama=?, nip=?, mapel_id=?, wali_kelas_id=?, email=?, hp=?, pendidikan=?, jabatan=?, foto=?, user_id=? "
               + "WHERE guru_id=?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, guru.getNama());
        stmt.setString(2, guru.getNip());
        stmt.setInt(3, guru.getMapelId());
        stmt.setInt(4, guru.getWaliKelasId());
        stmt.setString(5, guru.getEmail());
        stmt.setString(6, guru.getHp());
        stmt.setString(7, guru.getPendidikan());
        stmt.setString(8, guru.getJabatan());
        stmt.setString(9, guru.getFoto());
        stmt.setInt(10, guru.getUserId());
        stmt.setInt(11, guru.getGuruId()); // ID untuk WHERE clause

        stmt.executeUpdate();
    }
}


    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM guru WHERE guru_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private static Guru extractGuru(ResultSet rs) throws SQLException {
        Guru guru = new Guru();
        guru.setGuruId(rs.getInt("guru_id"));
        guru.setUserId(rs.getInt("user_id"));
        guru.setNama(rs.getString("nama"));
        guru.setNip(rs.getString("nip"));
        guru.setHp(rs.getString("hp"));
        guru.setEmail(rs.getString("email"));
        guru.setPendidikan(rs.getString("pendidikan"));
        guru.setJabatan(rs.getString("jabatan"));
        guru.setFoto(rs.getString("foto"));
        guru.setMapelId(rs.getInt("mapel_id"));
        guru.setWaliKelasId(rs.getInt("wali_kelas_id"));
        
            // 🟢 Tambahkan baris ini untuk mengisi data tambahan (nama mapel dan nama wali kelas)
        guru.setNamaMapel(rs.getString("mata_pelajaran"));
        guru.setNamaWaliKelas(rs.getString("nama_wali_kelas"));
    
        return guru;
    }
    
    
    public static List<Guru> getAllLengkap() {
    List<Guru> list = new ArrayList<>();
    String sql = "SELECT g.*, m.nama_mapel AS mata_pelajaran, k.nama_kelas AS nama_wali_kelas " +
                 "FROM guru g " +
                 "LEFT JOIN mata_pelajaran m ON g.mapel_id = m.mapel_id " +
                 "LEFT JOIN kelas k ON g.wali_kelas_id = k.kelas_id";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Guru g = extractGuru(rs);
            g.setMataPelajaran(rs.getString("mata_pelajaran"));
            g.setWaliKelas(rs.getString("nama_wali_kelas"));
            list.add(g);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
