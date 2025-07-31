package com.tadikamesra.controller;

import com.tadikamesra.model.Guru;
import com.tadikamesra.model.Pengumuman;
import com.tadikamesra.model.Jadwal;
import com.tadikamesra.dao.GuruDAO;
import com.tadikamesra.dao.JadwalDAO;
import com.tadikamesra.dao.PengumumanDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BerandaGuruServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Ambil session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Object userIdObj = session.getAttribute("userId");
        if (!(userIdObj instanceof Integer)) {
            System.out.println("[ERROR] Atribut session 'userId' bukan Integer.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = (Integer) userIdObj;
        System.out.println("[DEBUG] userId dari session: " + userId);

        // ✅ Ambil data guru berdasarkan userId
        Guru guru = GuruDAO.getGuruByUserId(userId);
        if (guru == null) {
            System.out.println("[ERROR] Guru tidak ditemukan untuk userId: " + userId);
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        System.out.println("[DEBUG] Guru ditemukan: " + guru.getNama());

        // ✅ Ambil pengumuman untuk guru
        List<Pengumuman> pengumumanList = PengumumanDAO.getRecentPengumumanFor("guru");

        // ✅ Ambil seluruh jadwal guru
        List<Jadwal> semuaJadwal = JadwalDAO.getByGuruId(guru.getGuruId());

        // ✅ Hari ini
        String hariIni = getHariIndonesia(LocalDate.now().getDayOfWeek().name());

        // ✅ Filter jadwal untuk hari ini
        List<Jadwal> jadwalHariIni = semuaJadwal.stream()
                .filter(j -> hariIni.equalsIgnoreCase(j.getHari()))
                .collect(Collectors.toList());

        // ✅ Kirim semua data ke JSP
        request.setAttribute("namaUser", guru.getNama());
        request.setAttribute("foto", guru.getFoto());
        request.setAttribute("nip", guru.getNip());
        request.setAttribute("email", guru.getEmail());
        request.setAttribute("hp", guru.getHp());
        request.setAttribute("mapel", guru.getNamaMapel());
        request.setAttribute("jabatan", guru.getJabatan());
        request.setAttribute("pendidikan", guru.getPendidikan());

        request.setAttribute("pengumumanList", pengumumanList);
        request.setAttribute("jadwalHariIni", jadwalHariIni);

        request.setAttribute("guru", guru);
        // ✅ Forward ke JSP
        request.getRequestDispatcher("/guru/BerandaGuru.jsp").forward(request, response);
    }

    private String getHariIndonesia(String dayEn) {
        switch (dayEn) {
            case "MONDAY": return "Senin";
            case "TUESDAY": return "Selasa";
            case "WEDNESDAY": return "Rabu";
            case "THURSDAY": return "Kamis";
            case "FRIDAY": return "Jumat";
            case "SATURDAY": return "Sabtu";
            case "SUNDAY": return "Minggu";
            default: return "";
        }
    }
}
