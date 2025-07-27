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
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password); // hashing nanti disarankan
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new User(
                rs.getInt("user_id"),              // <- disesuaikan
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
            );
        }
        return null;
    }
}
