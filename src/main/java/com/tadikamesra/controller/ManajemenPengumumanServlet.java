package com.tadikamesra.controller;

import com.tadikamesra.dao.PengumumanDAO;
import com.tadikamesra.model.Pengumuman;
import com.tadikamesra.util.DBConnection;
import com.tadikamesra.model.Siswa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ManajemenPengumumanServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");

        if ("delete".equals(action) && idParam != null) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM pengumuman WHERE pengumuman_id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idParam));
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/ManajemenPengumuman");
            return;
        }

        // Ambil semua pengumuman
        List<Pengumuman> daftarPengumuman = PengumumanDAO.getAll();

        // Untuk mode edit
        if (idParam != null && action == null) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT * FROM pengumuman WHERE pengumuman_id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idParam));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Pengumuman p = new Pengumuman();
                    p.setPengumumanId(rs.getInt("pengumuman_id"));
                    p.setJudul(rs.getString("judul"));
                    p.setIsi(rs.getString("isi"));
                    p.setTanggal(rs.getDate("tanggal"));
                    p.setDitujukanUntuk(rs.getString("ditujukan_untuk"));
                    req.setAttribute("pengumuman", p);
                }

                rs.close();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        req.setAttribute("daftarPengumuman", daftarPengumuman);
        req.getRequestDispatcher("/admin/ManajemenPengumuman.jsp").forward(req, resp);
    }

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false); // gunakan false agar tidak membuat session baru
    if (session == null || session.getAttribute("userId") == null) {
        // Tidak ada session atau belum login
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
        return;
    }

    // Lanjutkan proses simpan/update
    String idParam = req.getParameter("id");
    String judul = req.getParameter("judul");
    String isi = req.getParameter("isi");
    String tanggal = req.getParameter("tanggal");
    String ditujukan = req.getParameter("ditujukanUntuk");

    try (Connection conn = DBConnection.getConnection()) {
        if (idParam == null || idParam.isEmpty()) {
            String sql = "INSERT INTO pengumuman (judul, isi, tanggal, ditujukan_untuk) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, judul);
            ps.setString(2, isi);
            ps.setDate(3, java.sql.Date.valueOf(tanggal));
            ps.setString(4, ditujukan);
            ps.executeUpdate();
            ps.close();
        } else {
            String sql = "UPDATE pengumuman SET judul=?, isi=?, tanggal=?, ditujukan_untuk=? WHERE pengumuman_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, judul);
            ps.setString(2, isi);
            ps.setDate(3, java.sql.Date.valueOf(tanggal));
            ps.setString(4, ditujukan);
            ps.setInt(5, Integer.parseInt(idParam));
            ps.executeUpdate();
            ps.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    resp.sendRedirect(req.getContextPath() + "/admin/ManajemenPengumuman");
}
}
