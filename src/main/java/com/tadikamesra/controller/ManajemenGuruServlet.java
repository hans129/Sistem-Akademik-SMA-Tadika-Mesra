package com.tadikamesra.controller;

import com.tadikamesra.dao.GuruDAO;
import com.tadikamesra.dao.KelasDAO;
import com.tadikamesra.dao.MapelDAO;
import com.tadikamesra.model.Guru;
import com.tadikamesra.model.Kelas;
import com.tadikamesra.model.Mapel;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ManajemenGuruServlet extends HttpServlet {

    private GuruDAO guruDAO;
    private MapelDAO mapelDAO;
    private KelasDAO kelasDAO;

    @Override
    public void init() throws ServletException {
        guruDAO = new GuruDAO();
        mapelDAO = new MapelDAO();
        kelasDAO = new KelasDAO();
    }

    // TAMPILKAN HALAMAN
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       List<Guru> daftarGuru = guruDAO.getAll();
        List<Mapel> daftarMapel = mapelDAO.getAll(); 
        List<Kelas> daftarKelas = kelasDAO.getAll();

        request.setAttribute("daftarGuru", daftarGuru);
        request.setAttribute("daftarMapel", daftarMapel);
        request.setAttribute("daftarKelas", daftarKelas);

        request.getRequestDispatcher("/admin/manajemenGuru.jsp").forward(request, response);
    }

    // PROSES TAMBAH / HAPUS
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String aksi = request.getParameter("aksi");

        if ("tambah".equalsIgnoreCase(aksi)) {
            String nama = request.getParameter("nama");
            String nip = request.getParameter("nip");
            int userId = Integer.parseInt(request.getParameter("user_id"));

            Guru guru = new Guru();
            guru.setNama(nama);
            guru.setNip(nip);
            guru.setUserId(userId);

            guruDAO.insert(guru);

        } else if ("hapus".equalsIgnoreCase(aksi)) {
            int guruId = Integer.parseInt(request.getParameter("guru_id"));
            guruDAO.delete(guruId);
        }

        response.sendRedirect(request.getContextPath() + "/admin/manajemenguru");
    }
}
