package com.tadikamesra.controller;

import com.tadikamesra.dao.GuruDAO;
import com.tadikamesra.dao.KelasDAO;
import com.tadikamesra.dao.MapelDAO;
import com.tadikamesra.dao.UserDAO;
import com.tadikamesra.model.Guru;
import com.tadikamesra.model.Kelas;
import com.tadikamesra.model.Mapel;
import com.tadikamesra.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/manajemenguru")
public class ManajemenGuruServlet extends HttpServlet {

    private GuruDAO guruDAO;
    private MapelDAO mapelDAO;
    private KelasDAO kelasDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            guruDAO = new GuruDAO();
            mapelDAO = new MapelDAO();
            kelasDAO = new KelasDAO();
            userDAO = new UserDAO(conn); // ✅ Gunakan koneksi langsung
        } catch (Exception e) {
            throw new ServletException("Error initializing DAO", e);
        }
    }

    // Tampilkan halaman JSP
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection conn = DBConnection.getConnection()) {
            UserDAO userDAO = new UserDAO(conn); // instansi ulang untuk list user

            List<Guru> daftarGuru = guruDAO.getAll();
            List<Mapel> daftarMapel = mapelDAO.getAll();
            List<Kelas> daftarKelas = kelasDAO.getAll();
            List<User> daftarUser = userDAO.getAll();

            request.setAttribute("daftarGuru", daftarGuru);
            request.setAttribute("daftarMapel", daftarMapel);
            request.setAttribute("daftarKelas", daftarKelas);
            request.setAttribute("daftarUser", daftarUser);
            request.setAttribute("userList", daftarUser); // untuk dropdown

            request.getRequestDispatcher("/admin/manajemenGuru.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // bisa diganti log framework untuk produksi
        }
    }

    // Proses tambah / hapus guru
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
