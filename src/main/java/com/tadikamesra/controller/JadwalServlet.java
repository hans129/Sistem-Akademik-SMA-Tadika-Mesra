package com.tadikamesra.controller;

import com.tadikamesra.dao.JadwalDAO;
import com.tadikamesra.model.Jadwal;
import com.tadikamesra.controller.DBConnection;
        
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet("/jadwal")
public class JadwalServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tadika_mesra";
    private static final String DB_USER = "admin01";
    private static final String DB_PASSWORD = "admin123";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/ManajemenJadwal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String namaKegiatan = request.getParameter("namaKegiatan");
        String waktu = request.getParameter("waktu");
        String jenisKegiatan = request.getParameter("jenisKegiatan");
        int kelasId = Integer.parseInt(request.getParameter("kelasId"));
        String tanggal = request.getParameter("tanggal");
        int pengampuId = Integer.parseInt(request.getParameter("pengampuId"));

        Jadwal jadwal = new Jadwal(namaKegiatan, waktu, jenisKegiatan, kelasId, tanggal, pengampuId);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            JadwalDAO dao = new JadwalDAO(conn);
            dao.insert(jadwal);
            response.sendRedirect("admin/ManajemenJadwal.jsp?status=sukses");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin/ManajemenJadwal.jsp?status=gagal");
        }
    }
}
