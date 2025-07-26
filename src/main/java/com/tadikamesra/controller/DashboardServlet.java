/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.tadikamesra.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Muhamad Suwandi
 */
@WebServlet("/admin/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            // Query COUNT dari database
            ResultSet rs;

            rs = stmt.executeQuery("SELECT COUNT(*) FROM siswa");
            rs.next(); request.setAttribute("countSiswa", rs.getInt(1));

            rs = stmt.executeQuery("SELECT COUNT(*) FROM guru");
            rs.next(); request.setAttribute("countGuru", rs.getInt(1));

            rs = stmt.executeQuery("SELECT COUNT(*) FROM pengumuman WHERE status = 'aktif'");
            rs.next(); request.setAttribute("countPengumuman", rs.getInt(1));

            rs = stmt.executeQuery("SELECT COUNT(*) FROM jadwal WHERE tanggal BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)");
            rs.next(); request.setAttribute("countJadwal", rs.getInt(1));

            request.getRequestDispatcher("/admin/DashboardAdmin.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}

