package com.tadikamesra.controller;

import com.tadikamesra.dao.GuruDAO;
import com.tadikamesra.model.Guru;

import javax.servlet.ServletException;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


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
    List<String> daftarMapel = mapelDAO.getAllNamaMapel(); // ← ini wajib

    request.setAttribute("daftarGuru", daftarGuru);
    request.setAttribute("daftarMapel", daftarMapel); // ← ini juga wajib

    request.getRequestDispatcher("/admin/manajemenGuru.jsp").forward(request, response);
}


    // Jika ingin nanti: POST untuk tambah/edit, DELETE, dll
}
