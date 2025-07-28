<%@ page import="com.tadikamesra.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("user");
    String username = user != null ? user.getUsername() : "Pengguna";
%>
<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Manajemen Jadwal - Tadika Mesra</title>

  <!-- Font Awesome CDN -->
  <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
    crossorigin="anonymous"
  />

  <style>
    /* Reset & basics */
    * {
      box-sizing: border-box;
    }
    body {
      margin: 0;
      font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f0f2f5;
      color: #333;
    }

    /* Wrapper */
    .wrapper {
      display: flex;
      height: 100vh;
      overflow: hidden;
    }

    /* Sidebar */
    .sidebar {
      width: 240px;
      background: #353a40;
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
      font-weight: 700;
      line-height: 1.2;
      text-align: left;
    }
    .sidebar nav a {
      display: flex;
      align-items: center;
      padding: 12px 16px 12px 20px;
      color: white;
      text-decoration: none;
      border-radius: 0 10px 10px 0;
      margin-bottom: 10px;
      position: relative;
      transition: background 0.3s;
    }
    .sidebar nav a::before {
      content: "";
      position: absolute;
      left: 0;
      top: 10%;
      height: 80%;
      width: 6px;
      background: transparent;
      border-radius: 10px;
      transition: background 0.3s;
    }
    .sidebar nav a.active::before {
      background: white;
    }
    .sidebar nav a.active,
    .sidebar nav a:hover {
      background: #4c545c;
    }
    .sidebar nav a i {
      width: 20px;
      margin-right: 10px;
      font-size: 16px;
    }

    /* Main content */
    .main {
      flex: 1;
      margin-left: 240px;
      transition: margin-left 0.3s ease;
      display: flex;
      flex-direction: column;
      height: 100vh;
    }
    .main.expanded {
      margin-left: 0;
    }

    /* Header */
    .header {
      height: 60px;
      background: white;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 20px;
      border-bottom: 1px solid #ddd;
      box-shadow: 0 2px 5px rgb(0 0 0 / 0.1);
      flex-shrink: 0;
    }
    .hamburger {
      font-size: 22px;
      color: #555;
      cursor: pointer;
    }
    .header h4 {
      margin: 0;
      font-weight: 600;
      color: #222;
      display: flex;
      align-items: center;
      gap: 8px;
      font-size: 18px;
    }
    .header .user-info {
      font-weight: 600;
      color: #444;
      font-size: 14px;
      user-select: none;
    }

    /* Container with form */
    .container {
      background-color: white;
      padding: 30px 40px;
      margin: 20px auto;
      max-width: 700px;
      border-radius: 12px;
      box-shadow: 0 6px 15px rgb(0 0 0 / 0.1);
      overflow-y: auto;
      flex-grow: 1;
    }
    .container h2 {
      margin-bottom: 30px;
      color: #222;
      font-weight: 700;
      text-align: center;
    }

    form {
      width: 100%;
    }

    .row {
      display: flex;
      gap: 24px;
      margin-bottom: 25px;
    }
    .form-group {
      flex: 1;
      display: flex;
      flex-direction: column;
    }
    label {
      margin-bottom: 8px;
      font-weight: 600;
      color: #444;
    }
    input[type="text"],
    input[type="date"] {
      padding: 12px 14px;
      border-radius: 8px;
      border: 1.5px solid #bbb;
      font-size: 1rem;
      transition: border-color 0.3s ease;
    }
    input[type="text"]:focus,
    input[type="date"]:focus {
      border-color: #007bff;
      outline: none;
      box-shadow: 0 0 6px rgba(0, 123, 255, 0.5);
    }

    .buttons {
      display: flex;
      justify-content: center;
      gap: 20px;
      margin-top: 10px;
    }
    button.btn {
      padding: 12px 30px;
      border: none;
      border-radius: 10px;
      font-size: 1.1rem;
      cursor: pointer;
      transition: background-color 0.3s ease;
      color: white;
      min-width: 120px;
    }
    button.btn-primary {
      background-color: #007bff;
    }
    button.btn-primary:hover {
      background-color: #0056b3;
    }
    button.btn-secondary {
      background-color: #6c757d;
    }
    button.btn-secondary:hover {
      background-color: #565e64;
    }
    button.btn-success {
      background-color: #28a745;
    }
    button.btn-success:hover {
      background-color: #1e7e34;
    }

    /* Responsive */
    @media (max-width: 768px) {
      .sidebar {
        transform: translateX(-100%);
        position: fixed;
        z-index: 2000;
      }
      .sidebar.show {
        transform: translateX(0);
      }
      .main {
        margin-left: 0;
      }
      .row {
        flex-direction: column;
      }
    }
  </style>
</head>
<body>
  <div class="wrapper">
    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
      <div class="brand">
        <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra" />
        <h1>Tadika<br />Mesra</h1>
      </div>
      <nav>
        <a href="${pageContext.request.contextPath}/admin/beranda" class="nav-item">
          <i class="fa-solid fa-house"></i> Beranda
        </a>
        <a href="#" class="nav-item">
          <i class="fa-solid fa-envelope"></i> Manajemen Pengumuman
        </a>
        <a href="${pageContext.request.contextPath}/admin/ManajemenJadwal" class="nav-item">
          <i class="fa-solid fa-calendar-days"></i> Manajemen Jadwal
        </a>
        <a href="${pageContext.request.contextPath}/admin/ManajemenSiswa" class="nav-item">
          <i class="fa-solid fa-user-graduate"></i> Manajemen Siswa
        </a>
        <a href="${pageContext.request.contextPath}/admin/manajemenguru" class="nav-item">
          <i class="fa-solid fa-user-tie"></i> Manajemen Guru
        </a>
        <a href="${pageContext.request.contextPath}/Laporan-Akademik" class="nav-item">
          <i class="fa-solid fa-file-lines"></i> Manajemen Laporan Akademik
        </a>
        <a href="#" onclick="return confirmLogout();" class="nav-item">
          <i class="fa-solid fa-right-from-bracket"></i> Logout
        </a>
      </nav>
    </div>

    <!-- Main Content -->
    <div class="main" id="main">
      <!-- Header -->
      <div class="header">
        <div class="hamburger" onclick="toggleSidebar()">
          <i class="fa-solid fa-bars"></i>
        </div>
        <h4><i class="fa-regular fa-calendar-days"></i> Manajemen Jadwal</h4>
        <div class="user-info">👤 <strong><%= username %></strong></div>
      </div>

      <!-- Form container -->
      <div class="container">
        <h2>Form Jadwal Kegiatan</h2>
        <form method="post" action="<%=request.getContextPath()%>/jadwal">
          <div class="row">
            <div class="form-group">
              <label for="namaKegiatan">Nama Kegiatan</label>
              <input
                type="text"
                id="namaKegiatan"
                name="namaKegiatan"
                placeholder="Masukkan nama kegiatan"
                required
              />
            </div>
            <div class="form-group">
              <label for="waktu">Waktu</label>
              <input
                type="text"
                id="waktu"
                name="waktu"
                placeholder="Contoh: 08:00 - 10:00"
                required
              />
            </div>
          </div>

          <div class="row">
            <div class="form-group">
              <label for="jenisKegiatan">Jenis Kegiatan</label>
              <input
                type="text"
                id="jenisKegiatan"
                name="jenisKegiatan"
                placeholder="Misal: Ekstrakurikuler"
                required
              />
            </div>
            
          </div>

          <div class="row">
            <div class="form-group">
              <label for="tanggal">Hari / Tanggal</label>
              <input type="date" id="tanggal" name="tanggal" required />
            </div>
            <div class="form-group">
              <label for="pengampuId">Pengampu ID</label>
              <input
                type="text"
                id="pengampuId"
                name="pengampuId"
                placeholder="Masukkan ID pengampu"
                required
              />
            </div>
          </div>

          <div class="buttons">
            <button type="submit" class="btn btn-primary">Tambah</button>
            <button type="reset" class="btn btn-secondary">Hapus</button>
            <button
              type="button"
              class="btn btn-success"
              onclick="alert('Disimpan di browser')"
            >
              Simpan
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script>
    function toggleSidebar() {
      const sidebar = document.getElementById("sidebar");
      const main = document.getElementById("main");
      sidebar.classList.toggle("hidden");
      main.classList.toggle("expanded");
    }

    function confirmLogout() {
      if (confirm("Yakin ingin logout?")) {
        window.location.href = "${pageContext.request.contextPath}/logout";
      }
    }
  </script>
</body>
</html>