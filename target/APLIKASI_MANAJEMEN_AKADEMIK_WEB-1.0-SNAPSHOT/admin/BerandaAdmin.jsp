<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tadikamesra.model.User" %>
<%@ page import="com.tadikamesra.model.Pengumuman" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    List<Pengumuman> pengumumanList = (List<Pengumuman>) request.getAttribute("pengumumanList");
    User user = (User) session.getAttribute("user");
    String username = user != null ? user.getUsername() : "Pengguna";
%>

<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard Admin</title>

  <!-- Bootstrap & Font Awesome -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <style>
    body { margin: 0; font-family: 'Segoe UI', sans-serif; background: #f2f2f2; }
    .wrapper { display: flex; height: 100vh; overflow: hidden; }

    .sidebar {
      width: 240px; background: #2f3640; color: white; padding: 60px 20px 20px;
      position: fixed; top: 0; bottom: 0; left: 0; z-index: 1050; transition: all 0.3s ease;
    }

    .sidebar.hidden { margin-left: -240px; }

    .sidebar .brand {
      display: flex; align-items: center; justify-content: center; gap: 10px; margin-bottom: 30px;
    }

    .sidebar .brand img {
      width: 40px; height: 40px;
    }

    .sidebar .brand h1 {
      font-size: 18px; font-weight: bold; line-height: 1.2;
    }

    .sidebar nav a {
      display: block; padding: 12px 20px; color: white; text-decoration: none;
      margin-bottom: 10px; border-radius: 6px; transition: background 0.3s;
    }

    .sidebar nav a:hover, .sidebar nav a.active {
      background: #414b57;
    }

    .main {
      flex: 1; margin-left: 240px; padding: 20px; transition: all 0.3s ease;
    }

    .main.full { margin-left: 0; }

    .header {
      background: white; padding: 15px 20px; border-bottom: 1px solid #ddd;
      display: flex; justify-content: space-between; align-items: center;
    }

    .hamburger {
      font-size: 24px; cursor: pointer;
    }

    .content {
      margin-top: 20px;
    }

    .card {
      background: white; border-radius: 10px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      margin-bottom: 20px;
    }

    table {
      background: white; border-radius: 10px; overflow: hidden;
    }

    .table th, .table td {
      vertical-align: middle;
    }

    @media (max-width: 768px) {
      .sidebar { margin-left: -240px; }
      .main { margin-left: 0; }
    }
  </style>
</head>

<body>
<div class="wrapper">

  <!-- Sidebar -->
  <div class="sidebar" id="sidebar">
    <div class="brand">
      <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra">
      <h1>Tadika<br>Mesra</h1>
    </div>
    <nav>
      <a href="${pageContext.request.contextPath}/admin/beranda" class="nav-item active"><i class="fa-solid fa-house"></i> Beranda</a>
      <a href="#" class="nav-item"><i class="fa-solid fa-envelope"></i> Manajemen Pengumuman</a>
      <a href="#" class="nav-item"><i class="fa-solid fa-calendar-days"></i> Manajemen Jadwal</a>
      <a href="#" class="nav-item"><i class="fa-solid fa-user-graduate"></i> Manajemen Siswa</a>
      <a href="${pageContext.request.contextPath}/admin/manajemenguru" class="nav-item"><i class="fa-solid fa-user-tie"></i> Manajemen Guru</a>
      <a href="#" class="nav-item"><i class="fa-solid fa-file-lines"></i> Manajemen Laporan Akademik</a>
      <a href="#" onclick="return confirmLogout();" class="nav-item"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
    </nav>
  </div>

  <!-- Main -->
  <div class="main" id="main">
           <!-- Header -->
        <div class="header">
            <div class="hamburger" onclick="toggleSidebar()">
                <i class="fa-solid fa-bars"></i>
            </div>
            <h4 class="mb-0"><i class="fa-sharp fa-solid fa-house"></i>Dashboard</h4>
            <div>👤 <strong><%= username %></strong></div>
        </div>

    <!-- Content -->
    <div class="content">
      <h5>Selamat datang, <strong><%= username %></strong></h5>

      <div class="row my-4">
        <div class="col-md-3">
          <div class="card text-white bg-primary p-3">
            <div>Total Siswa: ${countSiswa}</div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="card text-white bg-success p-3">
            <div>Total Guru: ${countGuru}</div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="card text-white bg-warning p-3">
            <div>Pengumuman Aktif: ${countPengumuman}</div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="card text-white bg-danger p-3">
            <div>Jadwal Minggu Ini: ${countJadwal}</div>
          </div>
        </div>
      </div>

      <!-- Tabel Pengumuman -->
      <div class="card">
        <div class="card-header"><strong>Pengumuman Terbaru</strong></div>
        <div class="card-body p-0">
          <table class="table table-bordered table-hover align-middle mb-0">
            <thead class="table-dark">
              <tr>
                <th>Judul</th>
                <th>Isi</th>
                <th>Tanggal</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="p" items="${pengumumanList}">
                <tr>
                  <td>${p.judul}</td>
                  <td>${p.isi}</td>
                  <td>${p.tanggal}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>

    </div>
  </div>
</div>

<!-- JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
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
