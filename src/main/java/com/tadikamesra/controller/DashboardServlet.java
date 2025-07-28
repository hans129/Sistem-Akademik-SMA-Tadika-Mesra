package com.tadikamesra.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/admin/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Ambil total siswa
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM siswa")) {
                if (rs.next()) {
                    request.setAttribute("countSiswa", rs.getInt(1));
                }
            }

            // Ambil total guru
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM guru")) {
                if (rs.next()) {
                    request.setAttribute("countGuru", rs.getInt(1));
                }
            }

            // Ambil total pengumuman aktif
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM pengumuman WHERE status = 'aktif'")) {
                if (rs.next()) {
                    request.setAttribute("countPengumuman", rs.getInt(1));
                }
            }

            // Ambil total jadwal dalam 7 hari ke depan
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM jadwal WHERE tanggal BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)")) {
                if (rs.next()) {
                    request.setAttribute("countJadwal", rs.getInt(1));
                }
            }

            // Arahkan ke halaman dashboard
            request.getRequestDispatcher("/admin/DashboardAdmin.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
