package com.tadikamesra.dao;

import com.tadikamesra.model.Guru;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuruDAO {
    private Connection conn;

    public GuruDAO(Connection conn) {
        this.conn = conn;
    }

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

    try (PreparedStatement stmt = conn.prepareStatement(sql);
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

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("nama_mapel"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getAllNamaKelas() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT nama_kelas FROM kelas";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("nama_kelas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Guru guru) {
        String insertUserSql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        String insertGuruSql = "INSERT INTO guru (nama, nip, user_id, mapel_id) VALUES (?, ?, ?, ?)";
        String updateKelasSql = "UPDATE kelas SET wali_kelas_id = ? WHERE nama_kelas = ?";

        try {
            conn.setAutoCommit(false);

            int userId = getUserIdByUsername(guru.getUsername(), conn);
            if (userId == 0) {
                try (PreparedStatement userStmt = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                    userStmt.setString(1, guru.getUsername());
                    userStmt.setString(2, guru.getPassword());
                    userStmt.setString(3, "guru");
                    userStmt.executeUpdate();

                    try (ResultSet rs = userStmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            userId = rs.getInt(1);
                        }
                    }
                }
            }

            int mapelId = getMapelIdByNama(guru.getMataPelajaran(), conn);

            try (PreparedStatement insertStmt = conn.prepareStatement(insertGuruSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, guru.getNama());
                insertStmt.setString(2, guru.getNip());
                insertStmt.setInt(3, userId);
                insertStmt.setInt(4, mapelId);
                insertStmt.executeUpdate();

                try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int guruIdBaru = generatedKeys.getInt(1);

                        try (PreparedStatement updateKelasStmt = conn.prepareStatement(updateKelasSql)) {
                            updateKelasStmt.setInt(1, guruIdBaru);
                            updateKelasStmt.setString(2, guru.getWaliKelas());
                            updateKelasStmt.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }
    
    public void update(Guru guru) {
    String updateGuruSql = "UPDATE guru SET nama = ?, nip = ?, mapel_id = ? WHERE guru_id = ?";
    String updateUserSql = "UPDATE users SET username = ?, password = ? WHERE user_id = ?";
    String getUserIdSql = "SELECT user_id FROM guru WHERE guru_id = ?";
    String updateKelasSql = "UPDATE kelas SET wali_kelas_id = ? WHERE nama_kelas = ?";

    try {
        conn.setAutoCommit(false);

        int userId = 0;
        try (PreparedStatement stmt = conn.prepareStatement(getUserIdSql)) {
            stmt.setInt(1, guru.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }
        }

        try (PreparedStatement stmt = conn.prepareStatement(updateUserSql)) {
            stmt.setString(1, guru.getUsername());
            stmt.setString(2, guru.getPassword());
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        }

        int mapelId = getMapelIdByNama(guru.getMataPelajaran(), conn);

        try (PreparedStatement stmt = conn.prepareStatement(updateGuruSql)) {
            stmt.setString(1, guru.getNama());
            stmt.setString(2, guru.getNip());
            stmt.setInt(3, mapelId);
            stmt.setInt(4, guru.getId());
            stmt.executeUpdate();
        }

        try (PreparedStatement stmt = conn.prepareStatement(updateKelasSql)) {
            stmt.setInt(1, guru.getId());
            stmt.setString(2, guru.getWaliKelas());
            stmt.executeUpdate();
        }

        conn.commit();
    } catch (SQLException e) {
        e.printStackTrace();
        try {
            conn.rollback();
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
    }
}

    public Guru getById(int id) {
    String sql = "SELECT g.guru_id, g.nama, g.nip, u.username, u.password, mp.nama_mapel, k.nama_kelas " +
                 "FROM guru g " +
                 "LEFT JOIN users u ON g.user_id = u.user_id " +
                 "LEFT JOIN mata_pelajaran mp ON g.mapel_id = mp.mapel_id " +
                 "LEFT JOIN kelas k ON g.guru_id = k.wali_kelas_id " +
                 "WHERE g.guru_id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Guru guru = new Guru();
                guru.setId(rs.getInt("guru_id"));
                guru.setNama(rs.getString("nama"));
                guru.setNip(rs.getString("nip"));
                guru.setUsername(rs.getString("username"));
                guru.setPassword(rs.getString("password"));
                guru.setMataPelajaran(rs.getString("nama_mapel"));
                guru.setWaliKelas(rs.getString("nama_kelas"));
                return guru;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
    public void delete(int id) {
        String sql = "DELETE FROM guru WHERE guru_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUserIdByUsername(String username, Connection conn) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        }
        return 0;
    }

    private int getMapelIdByNama(String namaMapel, Connection conn) throws SQLException {
        String sql = "SELECT mapel_id FROM mata_pelajaran WHERE nama_mapel = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, namaMapel);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("mapel_id");
                }
            }
        }
        return 0;
    }
}
