<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tadikamesra.model.Guru" %>
<%@ page import="java.util.List" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    
    String namaUser = ""; // ✅ Deklarasi variabel di awal
        
    try (java.sql.Connection conn = com.tadikamesra.util.DBConnection.getConnection()) {
        if (conn != null) {
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
        }
    } catch (Exception e) {
        out.println("❗ ERROR mengambil nama user: " + e.getMessage());
        e.printStackTrace();
    }

    List<Guru> daftarGuru = (List<Guru>) request.getAttribute("daftarGuru");
    List<String> daftarMapel = (List<String>) request.getAttribute("daftarMapel");
    List<String> daftarKelas = (List<String>) request.getAttribute("daftarKelas");
    Guru guru = (Guru) request.getAttribute("guru");
%>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Manajemen Guru</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', sans-serif; }
        html, body { height: 100%; }

        #loader {
            position: fixed;
            top: 0; left: 0;
            width: 100vw;
            height: 100vh;
            background-color: rgba(255, 255, 255, 0.8);
            z-index: 9999;
            display: none;
            align-items: center;
            justify-content: center;
        }
        .spinner {
            width: 50px;
            height: 50px;
            border: 6px solid #ccc;
            border-top: 6px solid #2e3238;
            border-radius: 50%;
            animation: spin 0.8s linear infinite;
        }

        @keyframes spin {
            0%   { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        body {
            display: flex;
            background-color: #f5f6f8;
        }

        /* Sidebar */
        .sidebar {
            min-width: 240px;
            height: 100vh;
            background: #2e3238;
            color: white;
            padding: 20px;
            display: flex;
            flex-direction: column;
            scrollbar-width: none;
        }

        .sidebar .brand {
            text-align: center;
            margin-bottom: 40px;
        }

        .sidebar .brand img {
            width: 40px;
            height: 40px;
        }

        .sidebar .brand h1 {
            font-size: 18px;
            margin-top: 10px;
        }

        .sidebar nav a {
            display: flex; align-items: center; gap: 12px;
            color: white; text-decoration: none;
            padding: 14px 16px; margin-bottom: 10px;
            border-radius: 12px; font-size: 15px; position: relative;
            transition: background 0.3s ease;
        }

        .sidebar nav a.active,
        .sidebar nav a:hover {
            background-color: #44484e;
        }
        
        .sidebar nav a.active {
            background: #44484e; font-weight: bold; color: white;
        }
        .sidebar nav a.active::before {
            content: ""; position: absolute; left: 0; top: 10%; width: 8px; height: 80%;
            background-color: white; border-radius: 8px;
        }

        /* Main */
        .main {
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .header {
            height: 60px;
            background-color: #fff;
            padding: 0 20px;
            border-bottom: 1px solid #ddd;
            display: flex;
            justify-content: space-between;
            align-items: center;
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

        .table th, .table td {
            vertical-align: middle;
            text-align: center;
        }

        .btn-sm {
            margin-right: 5px;
        }

        .form-label {
            font-weight: 500;
        }
    </style>
</head>
<body>
<div id="loader">
  <div class="spinner"></div>
</div>
<div class="sidebar">
    <div class="brand">
        <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo">
        <h1>Tadika<br>Mesra</h1>
    </div>
    <nav>
        <a href="${pageContext.request.contextPath}/admin/BerandaAdmin"><i class="fa-solid fa-house me-2"></i> Beranda</a>
        <a href="#"><i class="fa-solid fa-envelope me-2"></i> Manajemen Pengumuman</a>
        <a href="#"><i class="fa-solid fa-calendar-days me-2"></i> Manajemen Jadwal</a>
        <a href="#"><i class="fa-solid fa-user-graduate me-2"></i> Manajemen Siswa</a>
        <a href="${pageContext.request.contextPath}/admin/ManajemenGuru" class="active"><i class="fa-solid fa-user-tie me-2"></i> Manajemen Guru</a>
        <a href="#"><i class="fa-solid fa-file-lines me-2"></i> Manajemen Laporan Akademik</a>
        <a href="#" onclick="return confirmLogout();" class="nav-item"><i class="fa-solid fa-right-from-bracket me-2"></i> Logout</a>
    </nav>
</div>

<div class="main">
    <div class="header">
        <div><i class="fa-solid fa-bars me-2"></i> Manajemen Guru</div>
        <div><i class="fa-solid fa-user me-1"></i> <strong><%= namaUser %></strong></div>
    </div>

    <div class="content">

        <!-- Form Guru -->
        <div class="form-section">
            <h5 class="mb-3"><i class="fa-solid fa-user-plus me-2"></i>Tambah/Edit Data Guru</h5>
            <form action="${pageContext.request.contextPath}/admin/ManajemenGuru" method="post">
                <input type="hidden" name="action" value="simpan" />
                <input type="hidden" name="id" value="${guru.id}" />
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Nama</label>
                        <input type="text" name="nama" value="${guru != null ? guru.nama : ''}" class="form-control" required />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">NIP</label>
                        <input type="text" name="nip" value="${guru != null ? guru.nip : ''}" class="form-control" required />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Username</label>
                        <input type="text" name="username" value="${guru != null ? guru.username : ''}" class="form-control" required />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Password</label>
                        <input type="password" name="password" value="${guru.password}" class="form-control" required />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Mata Pelajaran</label>
                        <select name="mapel" class="form-select" required>
                            <option value="">-- Pilih Mapel --</option>
                            <c:forEach var="mapel" items="${daftarMapel}">
                                <option value="${mapel}" <c:if test="${guru != null && guru.mataPelajaran == mapel}">selected</c:if>>${mapel}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Wali Kelas</label>
                        <select name="waliKelas" class="form-select">
                            <option value="">-- Bukan Wali Kelas --</option>
                            <c:forEach var="kelas" items="${daftarKelas}">
                                <option value="${kelas}" <c:if test="${guru != null && guru.waliKelas == kelas}">selected</c:if>>${kelas}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary px-4">
                        <i class="fa-solid fa-save me-2"></i> Simpan
                    </button>
                </div>
            </form>
        </div>

        <!-- Tabel Guru -->
        <div class="form-section">
            <h5 class="mb-3"><i class="fa-solid fa-list me-2"></i>Data Guru</h5>
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Nama</th>
                            <th>NIP</th>
                            <th>Username</th>
                            <th>Mata Pelajaran</th>
                            <th>Wali Kelas</th>
                            <th>Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="g" items="${daftarGuru}">
                            <tr>
                                <td>${g.id}</td>
                                <td>${g.nama}</td>
                                <td>${g.nip}</td>
                                <td>${g.username}</td>
                                <td>${g.mataPelajaran}</td>
                                <td>${g.waliKelas}</td>

                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/admin/ManajemenGuru" class="d-inline">
                                            <input type="hidden" name="action" value="delete" />
                                            <input type="hidden" name="id" value="${g.id}" />
                                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Yakin ingin menghapus?');">
                                                <i class="fa-solid fa-trash"></i>
                                        </button>
                                    </form>

                                    <form method="get" action="${pageContext.request.contextPath}/admin/ManajemenGuru" class="d-inline">
                                        <input type="hidden" name="id" value="${g.id}" />
                                        <button type="submit" class="btn btn-warning btn-sm">
                                        <i class="fa-solid fa-pen-to-square"></i>
                                            </button>
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
  // Munculkan loader saat pindah halaman
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
