
<%@ page import="com.tadikamesra.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

    User user = (User) session.getAttribute("user");
    String username = user != null ? user.getUsername() : "Pengguna";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manajemen </title>
    
    <style>
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
      flex-direction: row; /* sejajarkan logo dan teks */
      align-items: center;
      justify-content: center;
      gap: 10px;
      margin: 0 0 30px; /* hilangkan margin atas, beri bawah */
    }

    .sidebar .brand img {
      width: 40px;
      height: 40px;
    }

    .sidebar .brand h1 {
      font-size: 18px;
      font-weight: bold;
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
      transition: background 0.3s;
      position: relative;
    }
    
    .sidebar nav a::before {
        content: '';
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
        body {
            font-family: sans-serif;
            margin: 40px;
        }
        .container {
            background-color: #f8fbff;
            padding: 30px;
            border-radius: 10px;
            width: 600px;
            margin: auto;
        }
        h2 {
            margin-bottom: 30px;
            color: #000;
        }
        .row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"], input[type="date"] {
            width: 260px;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #aaa;
        }
        .buttons {
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
        }
        .btn {
            background-color: #333;
            color: #fff;
            padding: 10px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #555;
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
        <a href="${pageContext.request.contextPath}/admin/beranda" class="nav-item active">
          <i class="fa-solid fa-house"></i> Beranda
        </a>
        <a href="#" class="nav-item"><i class="fa-solid fa-envelope"></i> Manajemen Pengumuman</a>
        <a href="#" class="nav-item"><i class="fa-solid fa-calendar-days"></i> Manajemen Jadwal</a>
        <a href="${pageContext.request.contextPath}/admin/ManajemenSiswa" class="nav-item">
          <i class="fa-solid fa-user-graduate"></i> Manajemen Siswa
        </a>
        <a href="${pageContext.request.contextPath}/admin/manajemenguru" class="nav-item">
          <i class="fa-solid fa-user-tie"></i> Manajemen Guru
        </a>
        <a href="#" class="nav-item"><i class="fa-solid fa-file-lines"></i> Manajemen Laporan Akademik</a>
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
        <h4 class="mb-0"><i class="fa-solid fa-tools"></i> Manajemen Siswa</h4>
        <div>👤 <strong><%= username %></strong></div>
      </div>

      <!-- Container untuk form -->
      <div class="container">
        <h2>Manajemen Jadwal</h2>
        <form method="post" action="<%=request.getContextPath()%>/jadwal">
          <div class="row">
            <div>
              <label>Nama Kegiatan</label>
              <input type="text" name="namaKegiatan" required />
            </div>
            <div>
              <label>Waktu</label>
              <input type="text" name="waktu" required />
            </div>
          </div>
          <div class="row">
            <div>
              <label>Jenis Kegiatan</label>
              <input type="text" name="jenisKegiatan" required />
            </div>
            <div>
              <label>Kelas ID</label>
              <input type="text" name="kelasId" required />
            </div>
          </div>
          <div class="row">
            <div>
              <label>Hari / Tanggal</label>
              <input type="date" name="tanggal" required />
            </div>
            <div>
              <label>Pengampu ID</label>
              <input type="text" name="pengampuId" required />
            </div>
          </div>
          <div class="buttons">
            <button type="submit" class="btn">Tambah</button>
            <button type="reset" class="btn">Hapus</button>
            <button type="button" class="btn" onclick="alert('Disimpan di browser')">Simpan</button>
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