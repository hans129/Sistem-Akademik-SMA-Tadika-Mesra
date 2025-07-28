
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tadikamesra.dao;
import com.tadikamesra.controller.DBConnection;
import com.tadikamesra.model.siswa;
import java.sql.*;
import java.util.*;

/**
 *
 * @author hp
 */
public class SiswaDAO {

    // CREATE
    public void insertSiswa(siswa s) {
        String sql = "INSERT INTO siswa(nama, nis, username, password, kelas) VALUES (?, ?, ?, ?, ?)";
        try {
    Connection conn = DBConnection.getConnection(); // pastikan ada class DBConnection
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getNama());
            ps.setString(2, s.getNis());
            ps.setString(3, s.getUsername());
            ps.setString(4, s.getPassword());
            ps.setString(5, s.getKelas());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public List<siswa> getAllSiswa() {
        List<siswa> list = new ArrayList<>();
        String sql = "SELECT * FROM siswa";
        try {
            Connection conn = DBConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                siswa s = new siswa();
                s.setIdSiswa(rs.getInt("id_siswa"));
                s.setNama(rs.getString("nama"));
                s.setNis(rs.getString("nis"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                s.setKelas(rs.getString("kelas"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public void updateSiswa(siswa s) {
        String sql = "UPDATE siswa SET nama=?, nis=?, username=?, password=?, kelas=? WHERE id_siswa=?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getNama());
            ps.setString(2, s.getNis());
            ps.setString(3, s.getUsername());
            ps.setString(4, s.getPassword());
            ps.setString(5, s.getKelas());
            ps.setInt(6, s.getIdSiswa());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteSiswa(int id) {
        String sql = "DELETE FROM siswa WHERE id_siswa=?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GET BY ID
    public siswa getSiswaById(int id) {
        String sql = "SELECT * FROM siswa WHERE id_siswa=?";
        siswa s = new siswa();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s.setIdSiswa(rs.getInt("id_siswa"));
                s.setNama(rs.getString("nama"));
                s.setNis(rs.getString("nis"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                s.setKelas(rs.getString("kelas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

   
}