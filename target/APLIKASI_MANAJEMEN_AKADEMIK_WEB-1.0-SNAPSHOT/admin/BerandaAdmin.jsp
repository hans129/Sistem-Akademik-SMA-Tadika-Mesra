<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tadikamesra.model.User" %>
<%@ page import="com.tadikamesra.model.Pengumuman" %>
<%@ page import="java.util.List" %>

<%
    List<Pengumuman> pengumumanList = (List<Pengumuman>) request.getAttribute("pengumumanList");
%>
<%
    User user = (User) session.getAttribute("user");
    String username = user != null ? user.getUsername() : "Pengguna";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



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
    * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', sans-serif; }
    body { background: #f9f3f3; }

    .wrapper {
      display: flex;
      height: 100vh;
      overflow: hidden;
    }

    /* Sidebar */
    .sidebar {
      width: 240px;
      background: #353A40;
      color: white;
      padding: 60px 20px 20px;
      display: flex;
      flex-direction: column;
      position: fixed;
      top: 0;
      bottom: 0;
      left: 0;
      z-index: 1050;
      transition: transform 0.3s ease;
    }

    .sidebar.hidden {
      transform: translateX(-100%);
    }

    .sidebar .brand {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
      gap: 10px;
      margin-bottom: 30px;
    }

    .sidebar .brand img {
      width: 40px;
      height: 40px;
    }

    .sidebar .brand h1 {
      font-size: 18px;
      font-weight: bold;
      line-height: 1.2;
    }

    .sidebar nav a {
      display: flex;
      align-items: center;
      padding: 12px 16px 12px 20px;
      color: white;
      text-decoration: none;
      border-radius: 0 10px 10px 0;
      margin-bottom: 10px;
      transition: background 0.3s;
      position: relative;
    }

    .sidebar nav a.active::before {
      background: white;
    }

    .sidebar nav a.active,
    .sidebar nav a:hover {
      background: #4C545C;
    }

    .sidebar nav a i {
      width: 20px;
      margin-right: 10px;
    }

    /* Main Content */
    .main {
    flex: 1;
    margin-left: 240px;
    transition: margin-left 0.3s ease;
    display: flex;
    flex-direction: column;
  }

  .sidebar.hidden {
    transform: translateX(-100%);
  }

  .main.expanded {
    margin-left: 0;
  }



    .header {
      height: 60px;
      background: white;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 20px;
      border-bottom: 1px solid #ddd;
    }

    .menu-toggle {
      font-size: 20px;
      color: #555;
      cursor: pointer;
    }

    .content {
      padding: 40px;
      flex: 1;
    }

    .card {
      background: white;
      border-radius: 10px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      height: 100%;
    }

    /* Responsif */
    @media (max-width: 768px) {
      .sidebar {
        transform: translateX(-100%);
      }

      .sidebar.show {
        transform: translateX(0);
      }

      .main {
        margin-left: 0;
      }
    }
  </style>
</head>
<body>

<div class="wrapper">
  <!-- Sidebar -->
  <div class="sidebar" id="sidebar">
    <div>
      <div class="brand">
        <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra">
        <h1>Tadika<br>Mesra</h1>
      </div>
      <nav>
        <a href="<%= request.getContextPath() %>/admin/beranda"><i class="fa-solid fa-house"></i>Beranda</a>
        <a href="#"><i class="fa-solid fa-envelope"></i> Manajemen Pengumuman</a>
        <a href="#"><i class="fa-solid fa-calendar-days"></i> Manajemen Jadwal</a>
        <a href="#"><i class="fa-solid fa-user-graduate"></i> Manajemen Siswa</a>
        <a href="${pageContext.request.contextPath}/admin/guru" class="nav-item"><i class="fa-solid fa-user-tie"></i> Manajemen Guru</a>
        <a href="#"><i class="fa-solid fa-file-lines"></i> Manajemen Laporan Akademik</a>
        <a href="#"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
      </nav>
    </div>
  </div>

  <!-- Main -->
  <div class="main" id="main">
    <div class="header">
      <span class="menu-toggle" id="menu-toggle"><i class="fa-solid fa-bars"></i> Dashboard</span>
      <div class="user">👤 <%= username %></div>
    </div>
    <div class="content">
  <div class="p-4">
    <h5>Selamat datang, <strong><%= username %></strong></h5>

    <div class="row my-4">
      <div class="col-md-3">
        <div class="card text-white bg-primary shadow">
          <div class="card-body">Total Siswa: ${countSiswa}</div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-white bg-success shadow">
          <div class="card-body">Total Guru: ${countGuru}</div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-white bg-warning shadow">
          <div class="card-body">Pengumuman Aktif: ${countPengumuman}</div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-white bg-danger shadow">
          <div class="card-body">Jadwal Minggu Ini: ${countJadwal}</div>
        </div>
      </div>
    </div>

    <div class="card mt-4">
      <div class="card-header"><strong>Pengumuman Terbaru</strong></div>
      <div class="card-body p-0">
        <table class="table table-bordered m-0">
          <thead class="table-light">
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
</div>

<!-- JS Toggle Sidebar -->
<script>
  const toggle = document.getElementById('menu-toggle');
  const sidebar = document.getElementById('sidebar');
  const main = document.getElementById('main');

    toggle.addEventListener('click', () => {
    sidebar.classList.toggle('hidden');
    main.classList.toggle('expanded');

  });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
