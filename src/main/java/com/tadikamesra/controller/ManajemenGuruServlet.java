package com.tadikamesra.controller;

import com.tadikamesra.model.Guru;
import com.tadikamesra.dao.GuruDAO;
import com.tadikamesra.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class ManajemenGuruServlet extends HttpServlet {
    private GuruDAO guruDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                throw new ServletException("Koneksi database null. Pastikan DBConnection berfungsi.");
            }
            guruDAO = new GuruDAO(conn);
        } catch (Exception e) {
            throw new ServletException("Gagal inisialisasi koneksi database: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Ambil parameter ID jika ada (untuk edit guru)
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    Guru guru = guruDAO.getById(id);
                    if (guru != null) {
                        request.setAttribute("guru", guru);
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("[WARN] ID guru tidak valid: " + idParam);
                }
            }

            // Ambil semua data untuk ditampilkan
            List<Guru> daftarGuru = guruDAO.getAll();
            List<String> daftarMapel = guruDAO.getAllNamaMapel();
            List<String> daftarKelas = guruDAO.getAllNamaKelas();

            // Kirim ke JSP
            request.setAttribute("daftarGuru", daftarGuru);
            request.setAttribute("daftarMapel", daftarMapel);
            request.setAttribute("daftarKelas", daftarKelas);

            // Tampilkan halaman JSP
            request.getRequestDispatcher("/admin/ManajemenGuru.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Gagal memproses permintaan: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("simpan".equals(action)) {
                // Ambil data form
                int id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                        ? Integer.parseInt(request.getParameter("id"))
                        : 0;
                String nama = request.getParameter("nama");
                String nip = request.getParameter("nip");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String mapel = request.getParameter("mapel");
                String waliKelas = request.getParameter("waliKelas");

                Guru guru = new Guru();
                guru.setId(id);
                guru.setNama(nama);
                guru.setNip(nip);
                guru.setUsername(username);
                guru.setPassword(password);
                guru.setMataPelajaran(mapel);
                guru.setWaliKelas(waliKelas != null && !waliKelas.isEmpty() ? waliKelas : null);

                if (id > 0) {
                    guruDAO.update(guru);
                } else {
                    guruDAO.insert(guru);
                }
                response.sendRedirect(request.getContextPath() + "/admin/ManajemenGuru");
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                guruDAO.delete(id);
                response.sendRedirect(request.getContextPath() + "/admin/ManajemenGuru");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action tidak dikenali.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Gagal memproses POST: " + e.getMessage(), e);
        }
    }
}
