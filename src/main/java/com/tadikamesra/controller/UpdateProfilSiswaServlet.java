package com.tadikamesra.controller;

import com.tadikamesra.model.Siswa;
import com.tadikamesra.dao.SiswaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@WebServlet("/UpdateProfilSiswaServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 2, maxRequestSize = 1024 * 1024 * 10)
public class UpdateProfilSiswaServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "foto_siswa";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Siswa siswa = (Siswa) session.getAttribute("siswa");

        if (siswa == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Ambil input dari form
        siswa.setNama(request.getParameter("nama"));
        siswa.setEmail(request.getParameter("email"));
        siswa.setAgama(request.getParameter("agama"));
        siswa.setJenisKelamin(request.getParameter("jenisKelamin"));
        siswa.setStatusPernikahan(request.getParameter("statusPernikahan"));
        siswa.setJumlahSaudara(parseIntSafe(request.getParameter("jumlahSaudara")));
        siswa.setAnakKe(parseIntSafe(request.getParameter("anakKe")));
        siswa.setTempatLahir(request.getParameter("tempatLahir"));
        siswa.setTanggalLahir(request.getParameter("tanggalLahir"));
        siswa.setKewarganegaraan(request.getParameter("kewarganegaraan"));
        siswa.setNoKK(request.getParameter("noKK"));
        siswa.setTipeIdentitas(request.getParameter("tipeIdentitas"));
        siswa.setNomorIdentitas(request.getParameter("nomorIdentitas"));
        siswa.setTelpRumah(request.getParameter("telpRumah"));
        siswa.setNoHp(request.getParameter("noHp"));

        // Alamat KTP
        siswa.setAlamatKtp(request.getParameter("alamatKtp"));
        siswa.setJalanKtp(request.getParameter("jalanKtp"));
        siswa.setProvinsiKtp(request.getParameter("provinsiKtp"));
        siswa.setKotaKtp(request.getParameter("kotaKtp"));
        siswa.setKecamatanKtp(request.getParameter("kecamatanKtp"));
        siswa.setKelurahanKtp(request.getParameter("kelurahanKtp"));
        siswa.setRtKtp(parseIntSafe(request.getParameter("rtKtp")));
        siswa.setRwKtp(parseIntSafe(request.getParameter("rwKtp")));
        siswa.setKodeposKtp(request.getParameter("kodeposKtp"));

        // Alamat Domisili
        siswa.setAlamatDomisili(request.getParameter("alamatDomisili"));
        siswa.setJalanDomisili(request.getParameter("jalanDomisili"));
        siswa.setProvinsiDomisili(request.getParameter("provinsiDomisili"));
        siswa.setKotaDomisili(request.getParameter("kotaDomisili"));
        siswa.setKecamatanDomisili(request.getParameter("kecamatanDomisili"));
        siswa.setKelurahanDomisili(request.getParameter("kelurahanDomisili"));
        siswa.setRtDomisili(parseIntSafe(request.getParameter("rtDomisili")));
        siswa.setRwDomisili(parseIntSafe(request.getParameter("rwDomisili")));
        siswa.setKodeposDomisili(request.getParameter("kodeposDomisili"));
        siswa.setStatusTinggal(request.getParameter("statusTinggal"));

        // Upload foto (jika ada)
        Part filePart = request.getPart("foto");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String savedFile = uploadPath + File.separator + siswa.getNisn() + "_" + fileName;
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(savedFile), StandardCopyOption.REPLACE_EXISTING);
                siswa.setFoto("foto_siswa/" + siswa.getNisn() + "_" + fileName);
            }
        }

        // Simpan ke DB
        SiswaDAO siswaDAO = new SiswaDAO();
        boolean updated = siswaDAO.updateProfilSiswa(siswa);

        if (updated) {
            session.setAttribute("siswa", siswa);
            response.sendRedirect("dataPribadi.jsp?status=sukses");
        } else {
            response.sendRedirect("dataPribadi.jsp?status=gagal");
        }
    }

    private int parseIntSafe(String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return 0;
        }
    }
}
