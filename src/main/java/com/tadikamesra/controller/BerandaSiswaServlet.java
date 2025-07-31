package com.tadikamesra.controller;

import com.tadikamesra.model.Siswa;
import com.tadikamesra.model.Jadwal;
import com.tadikamesra.model.Pengumuman;
import com.tadikamesra.dao.SiswaDAO;
import com.tadikamesra.dao.JadwalDAO;
import com.tadikamesra.dao.PengumumanDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class BerandaSiswaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        // Ambil data siswa berdasarkan userId
        Siswa siswa = SiswaDAO.getSiswaByUserId(userId);

        if (siswa == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Ambil jadwal hari ini berdasarkan kelas siswa
        List<Jadwal> jadwalHariIni = JadwalDAO.getJadwalHariIni(userId);

        // Ambil pengumuman untuk siswa
        List<Pengumuman> pengumumanList = PengumumanDAO.getRecentPengumumanFor("Siswa");

        // Kirim data ke JSP
        request.setAttribute("siswa", siswa);
        request.setAttribute("jadwalHariIni", jadwalHariIni);
        request.setAttribute("pengumumanList", pengumumanList);

        request.getRequestDispatcher("/siswa/BerandaSiswa.jsp").forward(request, response);
    }
}
