package com.tadikamesra.controller;

import com.tadikamesra.dao.KelasDAO;
import com.tadikamesra.dao.SiswaDAO;
import com.tadikamesra.dao.UserDAO;
import com.tadikamesra.model.User;
import com.tadikamesra.model.Siswa;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/ManajemenSiswa")
public class ManajemenSiswaServlet extends HttpServlet {
    private final SiswaDAO siswaDAO = new SiswaDAO();
    private final KelasDAO kelasDAO = new KelasDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try (Connection conn = DBConnection.getConnection()) {
            UserDAO userDAO = new UserDAO(conn);

            List<User> userList = userDAO.getAll();
            List<Siswa> siswaList = siswaDAO.getAll();
            List<?> kelasList = kelasDAO.getAll(); // Bisa diganti jadi List<Kelas> jika import model Kelas

            req.setAttribute("userList", userList);
            req.setAttribute("siswaList", siswaList);
            req.setAttribute("kelasList", kelasList);

            req.getRequestDispatcher("/admin/ManajemenSiswa.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace(); // Bisa diganti dengan log framework untuk produksi
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("tambah".equals(action)) {
            String nama = req.getParameter("nama");
            String nis = req.getParameter("nis");
            int kelasId = Integer.parseInt(req.getParameter("kelas_id"));
            int userId = 0;

            String userIdStr = req.getParameter("user_id");
            if (userIdStr != null && !userIdStr.isEmpty()) {
                userId = Integer.parseInt(userIdStr);
            }

            Siswa siswa = new Siswa(nama, nis, kelasId, userId);
            siswaDAO.insert(siswa);

        } else if ("hapus".equals(action)) {
            int siswaId = Integer.parseInt(req.getParameter("siswa_id"));
            siswaDAO.delete(siswaId);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/ManajemenSiswa");
    }
}
