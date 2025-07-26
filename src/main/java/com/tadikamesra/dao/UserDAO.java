/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tadikamesra.dao;
import java.sql.*;
import com.tadikamesra.model.User;
 
public class UserDAO {
        private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public User login(String username, String password) throws SQLException {
    User user = null;

    // Cek di tabel admin
    String adminSQL = "SELECT * FROM admin WHERE username = ? AND password = ?";
    PreparedStatement stmt = conn.prepareStatement(adminSQL);
    stmt.setString(1, username);
    stmt.setString(2, password);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
        user = new User(rs.getInt("id_admin"), rs.getString("username"), rs.getString("password"), "admin");
        return user;
    }

    // Cek di tabel guru
    String guruSQL = "SELECT * FROM guru WHERE username = ? AND password = ?";
    stmt = conn.prepareStatement(guruSQL);
    stmt.setString(1, username);
    stmt.setString(2, password);
    rs = stmt.executeQuery();
    if (rs.next()) {
        user = new User(rs.getInt("id_guru"), rs.getString("username"), rs.getString("password"), "guru");
        return user;
    }

    // Cek di tabel siswa
    String siswaSQL = "SELECT * FROM siswa WHERE username = ? AND password = ?";
    stmt = conn.prepareStatement(siswaSQL);
    stmt.setString(1, username);
    stmt.setString(2, password);
    rs = stmt.executeQuery();
    if (rs.next()) {
        user = new User(rs.getInt("id_siswa"), rs.getString("username"), rs.getString("password"), "siswa");
        return user;
    }

    return null;
}
}
