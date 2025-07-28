package com.tadikamesra.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Hapus session user
        HttpSession session = request.getSession(false); // hindari membuat session baru
        if (session != null) {
            session.invalidate();
        }

        // Redirect ke halaman login
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
