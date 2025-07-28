package com.tadikamesra.controller;

import com.tadikamesra.dao.UserDAO;
import com.tadikamesra.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ambil data dari form login
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Koneksi dan validasi user
            Connection conn = DBConnection.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            User user = userDAO.login(username, password);

            if (user != null) {
                // Jika login berhasil, simpan user ke session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                // Redirect ke halaman sesuai role
                switch (user.getRole()) {
                    case "admin":
                        response.sendRedirect("admin/BerandaAdmin.jsp");
                        break;
                    case "guru":
                        response.sendRedirect("guru/BerandaGuru.jsp");
                        break;
                    case "siswa":
                        response.sendRedirect("siswa/BerandaSiswa.jsp");
                        break;
                    default:
                        response.sendRedirect("error.jsp"); // Role tidak dikenali
                        break;
                }
            } else {
                // Jika login gagal, kembali ke login dengan pesan
                request.setAttribute("error", "Username atau Password salah.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
