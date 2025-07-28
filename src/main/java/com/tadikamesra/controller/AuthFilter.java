package com.tadikamesra.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
        // Tidak perlu inisialisasi khusus saat filter dibuat
    }

    @Override
    public void destroy() {
        // Tidak perlu pembersihan khusus saat filter dihancurkan
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // Cek apakah user sudah login (dalam hal ini, login sebagai guru)
        boolean loggedIn = (session != null && session.getAttribute("loggedGuru") != null);

        // Cek apakah ini request ke halaman login
        boolean isLoginRequest = req.getRequestURI().contains("login");

        if (loggedIn || isLoginRequest) {
            // Lanjutkan ke servlet berikutnya
            chain.doFilter(request, response);
        } else {
            // Redirect ke halaman login jika belum login
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
