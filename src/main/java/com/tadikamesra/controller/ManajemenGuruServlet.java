package com.tadikamesra.controller;

import com.tadikamesra.dao.GuruDAO;
import com.tadikamesra.model.Guru;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/guru")
public class ManajemenGuruServlet extends HttpServlet {
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
        request.getRequestDispatcher("/manajemenGuru.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        boolean success = true;
        String error = null;

        if ("add".equalsIgnoreCase(action)) {
            success = tambahGuru(request);
            if (!success) error = "NIP sudah digunakan.";
        } else if ("edit".equalsIgnoreCase(action)) {
            success = editGuru(request);
            if (!success) error = "NIP sudah digunakan oleh guru lain.";
        } else if ("delete".equalsIgnoreCase(action)) {
            hapusGuru(request);
        }

        if (!success) {
            List<Guru> daftarGuru = guruDAO.getAll();
            request.setAttribute("daftarGuru", daftarGuru);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/manajemenGuru.jsp").forward(request, response);
        } else {
            response.sendRedirect("guru");
        }
        
    }

    private boolean tambahGuru(HttpServletRequest request) {
        String nip = request.getParameter("nip");
        if (!guruDAO.isNipUnique(nip, null)) return false;

        Guru guru = new Guru();
        guru.setNama(request.getParameter("nama"));
        guru.setNip(nip);
        guru.setUsername(request.getParameter("username"));
        guru.setPassword(request.getParameter("password"));
        guru.setMataPelajaran(request.getParameter("mata_pelajaran"));

        String waliKelas = request.getParameter("wali_kelas");
        guru.setWaliKelas(waliKelas != null && !waliKelas.trim().isEmpty() ? waliKelas : null);

        guruDAO.insert(guru);
        return true;
    }

    private boolean editGuru(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String nip = request.getParameter("nip");
        if (!guruDAO.isNipUnique(nip, id)) return false;

        Guru guru = new Guru();
        guru.setId(id);
        guru.setNama(request.getParameter("nama"));
        guru.setNip(nip);
        guru.setUsername(request.getParameter("username"));

        String password = request.getParameter("password");
        guru.setPassword(password != null && !password.trim().isEmpty() ? password : null);

        guru.setMataPelajaran(request.getParameter("mata_pelajaran"));

        String waliKelas = request.getParameter("wali_kelas");
        guru.setWaliKelas(waliKelas != null && !waliKelas.trim().isEmpty() ? waliKelas : null);

        guruDAO.update(guru);
        return true;
    }

        private void hapusGuru(HttpServletRequest request) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                guruDAO.delete(id); // Pastikan ini dipanggil
            }
        }

}
