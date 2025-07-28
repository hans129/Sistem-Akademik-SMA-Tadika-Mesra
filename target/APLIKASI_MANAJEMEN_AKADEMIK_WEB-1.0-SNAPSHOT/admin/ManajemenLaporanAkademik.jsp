<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tadikamesra.model.User, java.util.*, com.tadikamesra.model.LaporanAkademik" %>
<%
    List<LaporanAkademik> laporanList = (List<LaporanAkademik>) request.getAttribute("laporanList");
    User user = (User) session.getAttribute("user");
    String username = user != null ? user.getUsername() : "Pengguna";     
%>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Manajemen Laporan Akademik</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background: #f9f3f3; font-family: 'Segoe UI', sans-serif; }
        .wrapper { display: flex; min-height: 100vh; }
        .sidebar { width: 240px; background: #353A40; color: white; padding: 60px 20px 20px; position: fixed; height: 100vh; }
        .sidebar .brand { display: flex; align-items: center; gap: 10px; margin-bottom: 30px; }
        .sidebar .brand img { width: 40px; height: 40px; }
        .sidebar .brand h1 { font-size: 18px; font-weight: bold; line-height: 1.2; }
        .sidebar nav a { display: flex; align-items: center; padding: 12px 16px; color: white; text-decoration: none; margin-bottom: 10px; border-radius: 10px; }
        .sidebar nav a:hover, .sidebar nav a.active { background: #4C545C; }
        .sidebar nav a i { margin-right: 10px; }
        .main { flex: 1; margin-left: 240px; display: flex; flex-direction: column; }
        .header { height: 60px; background: white; display: flex; justify-content: space-between; align-items: center; padding: 0 20px; border-bottom: 1px solid #ddd; }
        .content { padding: 40px; }
        .btn-dark { width: 100%; }
    </style>
</head>
<body>

<div class="wrapper">
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="brand">
            <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo">
            <h1>Tadika<br>Mesra</h1>
        </div>
        <nav>
            <a href="${pageContext.request.contextPath}/ManajemenLaporanAkademik">
  <i class="fa-solid fa-file-lines"></i> Manajemen Laporan Akademik
</a>

            <a href="${pageContext.request.contextPath}/admin/beranda"><i class="fa-solid fa-house"></i> Beranda</a>
            <a href="#"><i class="fa-solid fa-envelope"></i> Manajemen Pengumuman</a>
            <a href="${pageContext.request.contextPath}/jadwal"><i class="fa-solid fa-calendar-days"></i> Manajemen Jadwal</a>
            <a href="#"><i class="fa-solid fa-user-graduate"></i> Manajemen Siswa</a>
            <a href="${pageContext.request.contextPath}/admin/guru"><i class="fa-solid fa-user-tie"></i> Manajemen Guru</a>
            <a class="active" href="${pageContext.request.contextPath}/admin/LaporanAkademik"><i class="fa-solid fa-file-lines"></i> Manajemen Laporan Akademik</a>
            <a href="#"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
        </nav>
    </div>

    <!-- Main -->
    <div class="main">
        <div class="header">
            <span><i class="fa-solid fa-bars"></i> Dashboard</span>
            <div>👤 <%= username %></div>
        </div>

        <div class="content">
            <h4 class="mb-4">Manajemen Laporan Akademik</h4>
            <form method="post" action="LaporanAkademikServlet" class="row g-3">
                <div class="col-md-4">
                    <label class="form-label">Pilih Kelas</label>
                    <select name="kelasId" class="form-select">
                        <option value="1">TIF RP 24A</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Pilih Siswa</label>
                    <select name="siswaId" class="form-select">
                        <option value="1">Budi Santoso</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Semester</label>
                    <select name="semester" class="form-select">
                        <option value="Ganjil">Semester Ganjil</option>
                        <option value="Genap">Semester Genap</option>
                    </select>
                </div>
                <div class="col-12 d-flex gap-3">
                    <button type="submit" class="btn btn-dark">Tampilkan</button>
                    <div class="d-flex gap-2 align-items-center">
                        <select name="format" class="form-select">
                            <option value="pdf">PDF</option>
                            <option value="pdf">World</option>
                        </select>
                        <button type="submit" class="btn btn-dark">Export</button>
                    </div>
                </div>

                <div class="col-12 mt-4">
                    <table class="table table-bordered table-striped">
                        <thead class="table-light">
                        <tr>
                            <th>Mata Pelajaran</th><th>Kelas</th><th>Absen</th><th>Tugas</th><th>Quiz</th>
                            <th>UTS</th><th>UAS</th><th>Nilai</th><th>Grade</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (laporanList != null) {
                                for (LaporanAkademik la : laporanList) {
                        %>
                        <tr>
                            <td><%= la.getNamaMapel() %></td>
                            <td><%= la.getNamaKelas() %></td>
                            <td><%= la.getAbsen() %></td>
                            <td><%= la.getTugas() %></td>
                            <td><%= la.getQuiz() %></td>
                            <td><%= la.getUts() %></td>
                            <td><%= la.getUas() %></td>
                            <td><%= la.getNilai() %></td>
                            <td><%= la.getGrade() %></td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr><td colspan="9" class="text-center">Silakan filter data untuk menampilkan laporan.</td></tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
