package com.tadikamesra.controller;

import com.tadikamesra.dao.GuruDAO;
import com.tadikamesra.model.Guru;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/guru")
public class GuruServlet extends HttpServlet {
    private GuruDAO guruDAO;

    @Override
    public void init() throws ServletException {
        guruDAO = new GuruDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Guru> daftarGuru = guruDAO.getAll();
        request.setAttribute("daftarGuru", daftarGuru);
        request.getRequestDispatcher("/admin/manajemenGuru.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("_method");

        if ("DELETE".equalsIgnoreCase(method)) {
            int id = Integer.parseInt(request.getParameter("id"));
            guruDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/guru");
            return;
        }

        // Ambil parameter dari form
        String idStr = request.getParameter("id");
        String nama = request.getParameter("nama");
        String nip = request.getParameter("nip");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String mataPelajaran = request.getParameter("mata_pelajaran");
        String waliKelas = request.getParameter("wali_kelas");

        // wali_kelas boleh kosong, tetapi null-kan jika kosong
        if (waliKelas != null && waliKelas.trim().isEmpty()) {
            waliKelas = null;
        }

        // Validasi unik NIP
        Integer excludeId = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : null;
        boolean isUnique = guruDAO.isNipUnique(nip, excludeId);

        if (!isUnique) {
            request.setAttribute("error", "NIP sudah digunakan!");
            request.setAttribute("daftarGuru", guruDAO.getAll());
            request.getRequestDispatcher("/admin/manajemenGuru.jsp").forward(request, response);
            return;
        }

        Guru guru = new Guru();
        guru.setNama(nama);
        guru.setNip(nip);
        guru.setUsername(username);
        guru.setPassword(password);
        guru.setMataPelajaran(mataPelajaran);
        guru.setWaliKelas(waliKelas);

        if (idStr == null || idStr.isEmpty()) {
            guruDAO.insert(guru);
        } else {
            guru.setId(Integer.parseInt(idStr));
            guruDAO.update(guru);
        }

        response.sendRedirect(request.getContextPath() + "/admin/guru");
    }
}
