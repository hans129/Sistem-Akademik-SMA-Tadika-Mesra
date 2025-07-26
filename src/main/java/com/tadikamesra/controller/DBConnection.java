package com.tadikamesra.controller;

import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sistemakademik2",
                "root",
                ""
            );
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }
}
