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
        guruDAO = new GuruDAO(); // Inisialisasi DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Guru> daftarGuru = guruDAO.getAllGuru();
        request.setAttribute("daftarGuru", daftarGuru);
        request.getRequestDispatcher("/manajemenGuru.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            tambahGuru(request);
        } else if ("edit".equalsIgnoreCase(action)) {
            editGuru(request);
        } else if ("delete".equalsIgnoreCase(action)) {
            hapusGuru(request);
        }

        response.sendRedirect("guru");
    }

    private void tambahGuru(HttpServletRequest request) {
        Guru guru = new Guru();
        guru.setNama(request.getParameter("nama"));
        guru.setNip(request.getParameter("nip"));
        guru.setUsername(request.getParameter("username"));
        guru.setPassword(request.getParameter("password"));
        guru.setMataPelajaran(request.getParameter("mata_pelajaran"));
        guru.setWaliKelas(request.getParameter("wali_kelas"));
        guruDAO.insert(guru);
    }

    private void editGuru(HttpServletRequest request) {
        Guru guru = new Guru();
        guru.setId(Integer.parseInt(request.getParameter("id")));
        guru.setNama(request.getParameter("nama"));
        guru.setNip(request.getParameter("nip"));
        guru.setUsername(request.getParameter("username"));
        guru.setPassword(request.getParameter("password")); // Bisa kosong
        guru.setMataPelajaran(request.getParameter("mata_pelajaran"));
        guru.setWaliKelas(request.getParameter("wali_kelas"));
        guruDAO.update(guru);
    }

    private void hapusGuru(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        guruDAO.delete(id);
    }
}
