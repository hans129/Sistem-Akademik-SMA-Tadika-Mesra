package com.tadikamesra.controller;

import com.tadikamesra.dao.GuruDAO;
import com.tadikamesra.model.Guru;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ManajemenGuruServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            GuruDAO dao = new GuruDAO(conn);
            List<Guru> list = dao.getAll();
            request.setAttribute("guruList", list);
            RequestDispatcher rd = request.getRequestDispatcher("admin/manajemenGuru.jsp");
            rd.forward(request, response);
        } catch (SQLException e) {
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nama = request.getParameter("nama");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String mataPelajaran = request.getParameter("mataPelajaran");
        String waliKelas = request.getParameter("waliKelas");

        try {
            Connection conn = DBConnection.getConnection();
            Guru g = new Guru();
            g.setNama(nama);
            g.setUsername(username);
            g.setPassword(password);
            g.setMataPelajaran(mataPelajaran);
            g.setWaliKelas(waliKelas);

            GuruDAO dao = new GuruDAO(conn);
            dao.insert(g);
            response.sendRedirect("manajemenGuru");
        } catch (SQLException e) {
        }
    }
}
