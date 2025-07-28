<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tadikamesra.model.Guru" %>
<%@ page import="com.tadikamesra.model.Mapel" %>
<%@ page import="com.tadikamesra.model.Kelas" %>
<%@ page import="com.tadikamesra.model.User" %>

<%
    List<Guru> daftarGuru = (List<Guru>) request.getAttribute("daftarGuru");
    List<Mapel> daftarMapel = (List<Mapel>) request.getAttribute("daftarMapel");
    List<Kelas> daftarKelas = (List<Kelas>) request.getAttribute("daftarKelas");
    User user = (User) session.getAttribute("user");
    String username = user != null ? user.getUsername() : "Pengguna";
%>

<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manajemen Guru</title>

  <!-- Bootstrap & Font Awesome -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <style>
    body {
      margin: 0;
      font-family: 'Segoe UI', sans-serif;
      background: #f2f2f2;
    }

    .wrapper {
      display: flex;
      height: 100vh;
      overflow: hidden;
    }

    .sidebar {
      width: 240px;
      background: #2f3640;
      color: white;
      padding: 60px 20px 20px;
      position: fixed;
      top: 0;
      bottom: 0;
      left: 0;
      z-index: 1050;
      transition: all 0.3s ease;
    }

    .sidebar.hidden {
      margin-left: -240px;
    }

    .sidebar .brand {
      display: flex;
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
      display: block;
      padding: 12px 20px;
      color: white;
      text-decoration: none;
      margin-bottom: 10px;
      border-radius: 6px;
      transition: background 0.3s;
    }

    .sidebar nav a:hover,
    .sidebar nav a.active {
      background: #414b57;
    }

    .main {
      flex: 1;
      margin-left: 240px;
      padding: 20px;
      transition: all 0.3s ease;
    }

    .main.full {
      margin-left: 0;
    }

    .header {
      background: white;
      padding: 15px 20px;
      border-bottom: 1px solid #ddd;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .hamburger {
      font-size: 24px;
      cursor: pointer;
    }

    .content {
      margin-top: 20px;
    }

    .card {
      background: white;
      border-radius: 10px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      margin-bottom: 20px;
    }

    table {
      background: white;
      border-radius: 10px;
      overflow: hidden;
    }

    .table th,
    .table td {
      vertical-align: middle;
    }

    @media (max-width: 768px) {
      .sidebar {
        margin-left: -240px;
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
    <div class="brand">
      <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra">
      <h1>Tadika<br>Mesra</h1>
    </div>
    <nav>
      <a href="${pageContext.request.contextPath}/admin/beranda" class="nav-item active"><i class="fa-solid fa-house"></i> Beranda</a>
      <a href="#" class="nav-item"><i class="fa-solid fa-envelope"></i> Manajemen Pengumuman</a>
      <a href="#" class="nav-item"><i class="fa-solid fa-calendar-days"></i> Manajemen Jadwal</a>
      <a href="${pageContext.request.contextPath}/admin/ManajemenSiswa" class="nav-item"><i class="fa-solid fa-user-graduate"></i> Manajemen Siswa</a>
      <a href="${pageContext.request.contextPath}/admin/manajemenguru" class="nav-item"><i class="fa-solid fa-user-tie"></i> Manajemen Guru</a>
      <a href="#" class="nav-item"><i class="fa-solid fa-file-lines"></i> Manajemen Laporan Akademik</a>
      <a href="#" onclick="return confirmLogout();" class="nav-item"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
    </nav>
  </div>

  <!-- Main -->
  <div class="main" id="main">
    <div class="content">
      <div class="header">
        <div class="hamburger" onclick="toggleSidebar()">
          <i class="fa-solid fa-bars"></i>
        </div>
        <h4 class="mb-0"><i class="fa-sharp fa-solid fa-tools"></i> Manajemen Guru</h4>
        <div>👤 <strong><%= username %></strong></div>
      </div>

      <!-- Form Tambah Guru -->
      <div class="mt-4">
        <form method="post" action="${pageContext.request.contextPath}/admin/manajemenguru">
          <input type="hidden" name="aksi" value="tambah">
          <div class="row g-3 align-items-center">
            <div class="col-md-3">
              <input type="text" class="form-control" name="nama" placeholder="Nama Guru" required>
            </div>
            <div class="col-md-2">
              <input type="text" class="form-control" name="nip" placeholder="NIP" required>
            </div>
            <div class="col-md-3">
              <select name="user_id" class="form-select" required>
                <option value="">Pilih User</option>
                <c:forEach var="user" items="${userList}">
                  <option value="${user.userId}">${user.role}</option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-2">
              <button type="submit" class="btn btn-primary">Tambah Guru</button>
            </div>
          </div>
        </form>
      </div>

      <!-- Tabel Daftar Guru -->
      <div class="mt-5">
        <table class="table table-bordered table-striped">
          <thead class="table-dark">
            <tr>
              <th>No</th>
              <th>Nama</th>
              <th>NIP</th>
              <th>User ID</th>
              <th>Aksi</th>
            </tr>
          </thead>
          <tbody>
            <%
              int no = 1;
              for (Guru g : daftarGuru) {
            %>
            <tr>
              <td><%= no++ %></td>
              <td><%= g.getNama() %></td>
              <td><%= g.getNip() %></td>
              <td><%= g.getUserId() %></td>
              <td>
                <form method="post" action="${pageContext.request.contextPath}/admin/manajemenguru" style="display:inline;">
                  <input type="hidden" name="aksi" value="hapus">
                  <input type="hidden" name="guru_id" value="<%= g.getGuruId() %>">
                  <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Yakin ingin menghapus?')">Hapus</button>
                </form>
              </td>
            </tr>
            <% } %>
          </tbody>
        </table>
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
