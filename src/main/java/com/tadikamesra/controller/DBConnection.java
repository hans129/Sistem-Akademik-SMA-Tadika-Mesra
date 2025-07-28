package com.tadikamesra.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    /**
     * Mengembalikan koneksi ke database MySQL tadika_mesra.
     * @return Connection object
     * @throws SQLException jika driver tidak ditemukan atau koneksi gagal
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Koneksi ke database
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tadika_mesra", // URL DB
                "root",                                      // Username DB
                ""                                           // Password DB
            );

        } catch (ClassNotFoundException e) {
            // Lempar error sebagai SQLException agar bisa ditangani seragam
            throw new SQLException("MySQL JDBC Driver tidak ditemukan.", e);
        }
    }
}
