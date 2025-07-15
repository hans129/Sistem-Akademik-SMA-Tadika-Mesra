package com.tadikamesra.controller;

import com.tadikamesra.model.Guru;
import com.tadikamesra.dao.GuruDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection conn = DBConnection.getConnection();
            GuruDAO dao = new GuruDAO(conn);
            List<Guru> guruList = dao.getAll();

            boolean success = false;
            Guru loginGuru = null;

            for (Guru g : guruList) {
                if (g.getUsername().equals(username) && g.getPassword().equals(password)) {
                    success = true;
                    loginGuru = g;
                    break;
                }
            }

            if (success) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedGuru", loginGuru);
                response.sendRedirect("manajemenGuru");
            } else {
                request.setAttribute("error", "Username atau Password salah.");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
