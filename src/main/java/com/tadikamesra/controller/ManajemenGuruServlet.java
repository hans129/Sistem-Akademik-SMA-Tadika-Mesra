package com.tadikamesra.controller;

import com.tadikamesra.model.Guru;
import com.tadikamesra.dao.GuruDAO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ManajemenGuruServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Ambil parameter ID jika ada (untuk edit guru)
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    Guru guru = GuruDAO.getById(id);
                    if (guru != null) {
                        request.setAttribute("guru", guru);
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("[WARN] ID guru tidak valid: " + idParam);
                }
            }

            // Ambil semua data untuk ditampilkan
            List<Guru> daftarGuru = GuruDAO.getAllLengkap();
            List<String> daftarMapel = GuruDAO.getAllNamaMapel();
            List<String> daftarKelas = GuruDAO.getAllNamaKelas();

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
            int id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                    ? Integer.parseInt(request.getParameter("id"))
                    : 0;
            String nama = request.getParameter("nama");
            String nip = request.getParameter("nip");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            int mapelId = Integer.parseInt(request.getParameter("mapel"));
            int waliKelasId = Integer.parseInt(request.getParameter("waliKelas"));

            Guru guru = new Guru();
            guru.setGuruId(id); // GANTI dari setId() ke setGuruId()
            guru.setNama(nama);
            guru.setNip(nip);
            guru.setUserId(0); // bisa kamu isi sesuai logika login
            guru.setMapelId(mapelId);
            guru.setWaliKelasId(waliKelasId);
            guru.setEmail(""); // atau request.getParameter("email") jika disediakan
            guru.setHp("");    // idem
            guru.setPendidikan(""); // idem
            guru.setJabatan("");    // idem
            guru.setFoto("");       // opsional

            // jika username dan password digunakan dalam Tabel Users
            // pastikan disimpan lewat relasi yang benar di DAO

            if (id > 0) {
                GuruDAO.update(guru);
            } else {
                GuruDAO.insert(guru);
            }
            response.sendRedirect(request.getContextPath() + "/admin/ManajemenGuru");

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            GuruDAO.delete(id);
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
