<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tadikamesra.model.Pengumuman" %>
<%@ page import="java.util.List" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    String namaUser = "";
    try (java.sql.Connection conn = com.tadikamesra.util.DBConnection.getConnection()) {
        String sqlNama = "SELECT nama FROM admin WHERE user_id = ? " +
                         "UNION SELECT nama FROM guru WHERE user_id = ? " +
                         "UNION SELECT nama FROM siswa WHERE user_id = ?";
        java.sql.PreparedStatement psNama = conn.prepareStatement(sqlNama);
        psNama.setInt(1, userId);
        psNama.setInt(2, userId);
        psNama.setInt(3, userId);
        java.sql.ResultSet rsNama = psNama.executeQuery();
        if (rsNama.next()) {
            namaUser = rsNama.getString("nama");
        }
        rsNama.close();
        psNama.close();
    } catch (Exception e) {
        out.println("⚠️ ERROR mengambil nama user: " + e.getMessage());
        e.printStackTrace();
    }

    List<Pengumuman> daftarPengumuman = (List<Pengumuman>) request.getAttribute("daftarPengumuman");
    Pengumuman pengumuman = (Pengumuman) request.getAttribute("pengumuman");
%>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Manajemen Pengumuman</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        <%-- (Style disamakan dengan ManajemenGuru.jsp) --%>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', sans-serif; }
        html, body { height: 100%; background: #f5f6f8; }

        #loader {
            position: fixed;
            top: 0; left: 0;
            width: 100vw; height: 100vh;
            background-color: rgba(255, 255, 255, 0.8);
            z-index: 9999; display: none;
            align-items: center; justify-content: center;
        }
        .spinner {
            width: 50px; height: 50px;
            border: 6px solid #ccc;
            border-top: 6px solid #2e3238;
            border-radius: 50%;
            animation: spin 0.8s linear infinite;
        }
        @keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }

        body { display: flex; }
        
                /* Sidebar */
        .sidebar.hidden {
        display: none;
        }

        .main.full {
        width: 100% !important;
        }

        .sidebar {
            min-width: 240px;
            height: 100vh;
            background: #2e3238;
            color: white;
            padding: 20px;
            display: flex;
            flex-direction: column;
        }
        .sidebar .brand { text-align: center; margin-bottom: 40px; }
        .sidebar .brand img { width: 40px; height: 40px; }
        .sidebar .brand h1 { font-size: 18px; margin-top: 10px; }
        .sidebar nav a {
        display: flex;
        align-items: center;
        gap: 12px;
        color: white;
        text-decoration: none;
        padding: 14px 16px;
        margin-bottom: 10px;
        border-radius: 12px;
        font-size: 15px;
        position: relative; /* <--- pastikan baris ini ADA */
        transition: background 0.3s ease;
        }
        .sidebar nav a.active,
        .sidebar nav a:hover { background: #44484e; font-weight: bold; }
        
        .sidebar nav a.active::before {
        content: "";
        position: absolute;
        left: 0;
        top: 10%;
        width: 8px;
        height: 80%;
        background-color: white;
        border-radius: 8px;
        }

        .main {
            flex: 1; display: flex; flex-direction: column;
        }
        .header {
            height: 60px;
            background: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 20px;
            border-bottom: 1px solid #ccc;
        }
        .content {
            padding: 30px 40px;
            overflow-y: auto;
        }

        .form-section {
            background: white;
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 30px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div id="loader"><div class="spinner"></div></div>

<div class="sidebar" id="sidebar">
    <div class="brand">
        <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra">
        <h1>Tadika<br>Mesra</h1>
    </div>
    <nav>
        <a href="${pageContext.request.contextPath}/admin/BerandaAdmin"><i class="fa-solid fa-house"></i> Beranda</a>
        <a href="${pageContext.request.contextPath}/admin/ManajemenPengumuman" class="active"><i class="fa-solid fa-envelope"></i> Manajemen Pengumuman</a>
        <a href="#"><i class="fa-solid fa-calendar-days"></i> Manajemen Jadwal</a>
        <a href="#"><i class="fa-solid fa-user-graduate"></i> Manajemen Siswa</a>
        <a href="${pageContext.request.contextPath}/admin/ManajemenGuru"><i class="fa-solid fa-user-tie"></i> Manajemen Guru</a>
        <a href="#"><i class="fa-solid fa-file-lines"></i> Manajemen Laporan Akademik</a>
        <a href="#" onclick="return confirmLogout();"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
    </nav>
</div>

<div class="main" id="main">
    <div class="header" onclick="toggleSidebar()" style="cursor: pointer;">
        <div><i class="fa-solid fa-bars me-2"></i> Manajemen Pengumuman</div>
        <div><i class="fa-solid fa-user me-1"></i> <strong><%= namaUser %></strong></div>
    </div>

    <div class="content">

        <!-- Form -->
        <div class="form-section">
            <h5 class="mb-3"><i class="fa-solid fa-envelope-circle-check me-2"></i>Tambah/Edit Pengumuman</h5>
            <form action="${pageContext.request.contextPath}/admin/ManajemenPengumuman" method="post">
                <input type="hidden" name="id" value="${pengumuman != null ? pengumuman.pengumumanId : ''}" />
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Judul</label>
                        <input type="text" name="judul" value="${pengumuman != null ? pengumuman.judul : ''}" class="form-control" required />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Tanggal</label>
                        <input type="date" name="tanggal" value="${pengumuman != null ? pengumuman.tanggal : ''}" class="form-control" required />
                    </div>
                    <div class="col-12">
                        <label class="form-label">Isi</label>
                        <textarea name="isi" class="form-control" rows="4" required>${pengumuman != null ? pengumuman.isi : ''}</textarea>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Ditujukan Untuk</label>
                        <select name="ditujukanUntuk" class="form-select" required>
                            <option value="">-- Pilih --</option>
                            <option value="Semua" ${pengumuman != null && pengumuman.ditujukanUntuk == 'Semua' ? 'selected' : ''}>Semua</option>
                            <option value="Guru" ${pengumuman != null && pengumuman.ditujukanUntuk == 'Guru' ? 'selected' : ''}>Guru</option>
                            <option value="Siswa" ${pengumuman != null && pengumuman.ditujukanUntuk == 'Siswa' ? 'selected' : ''}>Siswa</option>
                        </select>
                    </div>
                </div>
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary px-4">
                        <i class="fa-solid fa-paper-plane me-2"></i> Simpan
                    </button>
                </div>
            </form>
        </div>

        <!-- Tabel -->
        <div class="form-section">
            <h5 class="mb-3"><i class="fa-solid fa-list me-2"></i>Daftar Pengumuman</h5>
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Judul</th>
                            <th>Isi</th>
                            <th>Tanggal</th>
                            <th>Ditujukan</th>
                            <th>Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${daftarPengumuman}">
                            <tr>
                                <td>${p.pengumumanId}</td>
                                <td>${p.judul}</td>
                                <td>${p.isi}</td>
                                <td>${p.tanggal}</td>
                                <td>${p.ditujukanUntuk}</td>
                                <td>
                                    <form method="get" action="${pageContext.request.contextPath}/admin/ManajemenPengumuman" class="d-inline">
                                        <input type="hidden" name="id" value="${p.pengumumanId}" />
                                        <button type="submit" class="btn btn-warning btn-sm"><i class="fa-solid fa-pen-to-square"></i></button>
                                    </form>
                                    <form method="get" action="${pageContext.request.contextPath}/admin/ManajemenPengumuman" class="d-inline" onsubmit="return confirm('Yakin ingin menghapus?');">
                                        <input type="hidden" name="action" value="delete" />
                                        <input type="hidden" name="id" value="${p.pengumumanId}" />
                                        <button type="submit" class="btn btn-danger btn-sm"><i class="fa-solid fa-trash"></i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>

<script>
  window.addEventListener("beforeunload", function () {
    document.getElementById("loader").style.display = "flex";
  });
</script>
<script>
  function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    const main = document.getElementById('main');
    sidebar.classList.toggle('hidden');
    main.classList.toggle('full');
  }

  function confirmLogout() {
    if (confirm("Yakin ingin logout?")) {
      window.location.href = "${pageContext.request.contextPath}/logout";
    }
  }
</script>
</body>
</html>
