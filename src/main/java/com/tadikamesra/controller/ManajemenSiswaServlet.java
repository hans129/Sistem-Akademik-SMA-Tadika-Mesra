

package com.tadikamesra.controller;

import com.tadikamesra.dao.SiswaDAO;
import com.tadikamesra.model.siswa;

import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SiswaController")
public class ManajemenSiswaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SiswaDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new SiswaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                List<siswa> listSiswa = dao.getAllSiswa();
                request.setAttribute("listSiswa", listSiswa);
                RequestDispatcher rd = request.getRequestDispatcher("admin/ManajemenSiswa.jsp");
                rd.forward(request, response);
                break;

            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                dao.deleteSiswa(idDelete);
                response.sendRedirect("SiswaController?action=list");
                break;

            default:
                response.sendRedirect("SiswaController?action=list");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String idStr = request.getParameter("id_siswa");
        String nama = request.getParameter("nama");
        String nis = request.getParameter("nis");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String kelas = request.getParameter("kelas");

        siswa s = new siswa();
        s.setNama(nama);
        s.setNis(nis);
        s.setUsername(username);
        s.setPassword(password);
        s.setKelas(kelas);

        if (idStr == null || idStr.isEmpty()) {
            // INSERT siswa baru
            dao.insertSiswa(s);
        } else {
            // UPDATE siswa
            s.setIdSiswa(Integer.parseInt(idStr));
            dao.updateSiswa(s);
        }

        response.sendRedirect("SiswaController?action=list");
    }
}

