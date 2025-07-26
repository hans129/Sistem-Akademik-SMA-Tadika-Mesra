package com.tadikamesra.controller;

import com.tadikamesra.dao.DashboardDAO;
import com.tadikamesra.model.Pengumuman;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BerandaAdminServlet extends HttpServlet {
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    DashboardDAO dao = null;
    try {
        dao = new DashboardDAO();

        int countSiswa = dao.getTotalSiswa();
        int countGuru = dao.getTotalGuru();
        int countPengumuman = dao.getTotalPengumumanAktif();
        int countJadwal = dao.getTotalJadwalMingguIni();
        List<Pengumuman> pengumumanList = dao.getPengumumanTerbaru();

        request.setAttribute("countSiswa", countSiswa);
        request.setAttribute("countGuru", countGuru);
        request.setAttribute("countPengumuman", countPengumuman);
        request.setAttribute("countJadwal", countJadwal);
        request.setAttribute("pengumumanList", pengumumanList);

    } catch (SQLException e) {
        e.printStackTrace(); // Sementara log ke console
        request.setAttribute("error", "Terjadi kesalahan saat mengambil data dashboard.");
    }

    request.getRequestDispatcher("/admin/BerandaAdmin.jsp").forward(request, response);
}

}
