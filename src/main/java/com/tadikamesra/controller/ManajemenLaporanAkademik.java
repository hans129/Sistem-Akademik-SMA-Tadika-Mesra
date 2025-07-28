package com.tadikamesra.controller;

import com.tadikamesra.dao.LaporanAkademikDAO;
import com.tadikamesra.model.LaporanAkademik;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/Laporan-Akademik") // Tambahkan anotasi agar dikenali di web.xml jika pakai annotation
public class ManajemenLaporanAkademik extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Untuk menangani karakter non-ASCII (misal nama siswa/guru)
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String kelasId = request.getParameter("kelasId");
        String siswaId = request.getParameter("siswaId");
        String semester = request.getParameter("semester");

        // Validasi agar tidak null (bisa diubah sesuai kebutuhan)
        if (kelasId == null) kelasId = "";
        if (siswaId == null) siswaId = "";
        if (semester == null) semester = "";

        // Ambil data laporan dari DAO
        LaporanAkademikDAO dao = new LaporanAkademikDAO();
        List<LaporanAkademik> laporanList = dao.getLaporanByFilter(kelasId, siswaId, semester);

        // Kirim ke JSP
        request.setAttribute("laporanList", laporanList);
        request.setAttribute("kelasId", kelasId);       // jika kamu ingin tetap menampilkan input terisi
        request.setAttribute("siswaId", siswaId);
        request.setAttribute("semester", semester);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/ManajemenLaporanAkademik.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect ke POST untuk konsistensi
        doPost(request, response);
    }
}
