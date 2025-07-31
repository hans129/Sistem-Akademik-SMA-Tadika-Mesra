package com.tadikamesra.controller;

import com.tadikamesra.model.User;
import com.tadikamesra.model.Guru;
import com.tadikamesra.model.Siswa;
import com.tadikamesra.dao.UserDAO;
import com.tadikamesra.dao.GuruDAO;
import com.tadikamesra.dao.SiswaDAO;
import com.tadikamesra.util.DBConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LoginServlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection conn = DBConnection.getConnection();
            UserDAO userDAO = new UserDAO(conn);

            User user = userDAO.login(username, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId()); // ✅ Tambahkan baris ini
                session.setAttribute("role", user.getRole());

                switch (user.getRole()) {
                    case "admin":
                        response.sendRedirect("admin/BerandaAdmin.jsp");
                        break;
                    case "guru":
                        Guru guru = GuruDAO.getGuruByUserId(user.getUserId()); // ambil biodata guru
                        session.setAttribute("guru", guru); // simpan ke session
                        response.sendRedirect("guru/BerandaGuru.jsp");
                        break;
                    case "siswa":
                        Siswa siswa = SiswaDAO.getSiswaByUserId(user.getUserId()); // ambil biodata siswa
                        session.setAttribute("siswa", siswa); // simpan ke session
                        response.sendRedirect("siswa/BerandaSiswa.jsp");
                        break;
                    default:
                        response.sendRedirect("error.jsp");
                }
            } else {
                request.setAttribute("error", "Username atau Password salah.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}