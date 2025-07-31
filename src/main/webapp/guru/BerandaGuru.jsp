<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="com.tadikamesra.model.Pengumuman" %>
<%@ page import="com.tadikamesra.dao.PengumumanDAO" %>
<%@ page import="com.tadikamesra.dao.JadwalDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tadikamesra.util.DBConnection" %>
<%@ page import="com.tadikamesra.model.Jadwal" %>
<%@ page import="com.tadikamesra.model.Guru" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="java.util.*, java.time.*, com.tadikamesra.model.*, com.tadikamesra.dao.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="java.util.*, java.time.*, com.tadikamesra.model.*, com.tadikamesra.dao.*" %>

<%
// Ambil userId dari session
Integer userId = (Integer) session.getAttribute("userId");

// Coba ambil guru dari attribute request
Guru guru = (Guru) request.getAttribute("guru");

// Jika null, ambil langsung dari DB
if (guru == null && userId != null) {
    guru = GuruDAO.getGuruByUserId(userId);
}

// Ambil data dari guru
String namaUser = guru != null ? guru.getNama() : "User";
String foto = guru != null && guru.getFoto() != null && !guru.getFoto().isEmpty()
    ? guru.getFoto() : "profile-placeholder.png";
String nip = guru != null ? guru.getNip() : "-";
String hp = guru != null ? guru.getHp() : "-";
String email = guru != null ? guru.getEmail() : "-";
String mapel = guru != null ? guru.getNamaMapel() : "-";

// Ambil pengumuman dari request (jika ada), kalau tidak ada ambil dari DAO
List<Pengumuman> pengumumanList = (List<Pengumuman>) request.getAttribute("pengumumanList");
if (pengumumanList == null) {
    pengumumanList = PengumumanDAO.getRecentPengumumanFor("guru");
}

// Ambil jadwal hari ini dari request
List<Jadwal> jadwalHariIni = (List<Jadwal>) request.getAttribute("jadwalHariIni");

// Kalau tidak ada, ambil dari DAO berdasarkan guru
if (jadwalHariIni == null && guru != null) {
    List<Jadwal> semuaJadwal = JadwalDAO.getByGuruId(guru.getGuruId());

    // Tentukan hari ini
String hariIni = "";
switch (LocalDate.now().getDayOfWeek()) {
    case MONDAY:
        hariIni = "Senin";
        break;
    case TUESDAY:
        hariIni = "Selasa";
        break;
    case WEDNESDAY:
        hariIni = "Rabu";
        break;
    case THURSDAY:
        hariIni = "Kamis";
        break;
    case FRIDAY:
        hariIni = "Jumat";
        break;
    case SATURDAY:
        hariIni = "Sabtu";
        break;
    case SUNDAY:
        hariIni = "Minggu";
        break;
}

    // Filter hanya jadwal hari ini
    jadwalHariIni = new ArrayList<>();
    for (Jadwal j : semuaJadwal) {
        if (j.getHari().equalsIgnoreCase(hariIni)) {
            jadwalHariIni.add(j);
        }
    }
}
%>





<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard Guru</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
  <link rel="stylesheet" href="https://cdn.datatables.net/buttons/2.3.6/css/buttons.dataTables.min.css">
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>

  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', sans-serif; }
    html, body { height: 100%; width: 100%; }
    body { display: flex; background: #f5f6f8; overflow: hidden; }

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

    .sidebar.hidden { display: none; }
    .main.full { width: 100%; }

    .sidebar {
      min-width: 240px;
      height: 100vh;
      background: #2e3238;
      color: white;
      padding: 20px;
      display: flex;
      flex-direction: column;
      scrollbar-width: none;
      transition: all 0.3s ease;
    }
    .sidebar::-webkit-scrollbar { display: none; }
    .sidebar .brand {
      display: flex; flex-direction: column; align-items: center;
      text-align: center; margin-bottom: 30px;
    }
    .sidebar .brand img { width: 40px; height: 40px; margin-bottom: 10px; }
    .sidebar .brand h1 { font-size: 18px; font-weight: bold; line-height: 1.2; }
    .sidebar nav a {
      display: flex; align-items: center; gap: 12px;
      color: white; text-decoration: none;
      padding: 14px 16px; margin-bottom: 10px;
      border-radius: 12px; font-size: 15px; position: relative;
      transition: background 0.3s ease;
    }
    .sidebar nav a.active {
      background: #44484e; font-weight: bold; color: white;
    }
    .sidebar nav a.active::before {
      content: ""; position: absolute; left: 0; top: 10%; width: 8px; height: 80%;
      background-color: white; border-radius: 8px;
    }
    .sidebar nav a:hover { background: #424850; }
    .sidebar nav a i {
      width: 20px; text-align: center; font-size: 16px;
    }

    .main { flex: 1; display: flex; flex-direction: column; transition: all 0.3s ease;}
    .header {
      height: 60px; background: #fff;
      display: flex; justify-content: space-between;
      align-items: center; padding: 0 20px;
      border-bottom: 1px solid #ccc;
    }
    .content { padding: 30px 40px; overflow-y: auto; }

    .biodata-section {
      width: 100%;
      padding: 20px;
      background: #222;
      border-radius: 12px;
      color: #fff;
      margin-top: 10px;
    }
    .biodata-wrapper {
      display: flex;
      gap: 20px;
      margin-top: 20px;
      align-items: stretch;
      flex-wrap: wrap;
    }
    .foto-container { flex: 0 0 200px; }
    .foto-guru {
      width: 100%;
      height: auto;
      object-fit: cover;
      border-radius: 16px;
      display: block;
    }
    .biodata-container { flex: 1; }
    h3 {
      font-size: 30px;
      font-weight: bold;
      color: #222;
      margin-bottom: 16px;
    }

    .biodata-section table {
      width: 100%;
      border-collapse: collapse;
    }
    .biodata-section td {
      padding: 8px 2%;
      border: none;
      color: #fff;
    }
    .biodata-section td:first-child {
      font-weight: bold;
      width: 30%;
      white-space: nowrap;
    }
    .biodata-section tr {
      background-color: transparent;
    }
    
    .biodata-table {
    border: 1px solid #ccc;
    padding: 10px;
    background-color: #2d2f34;
    border-radius: 10px;
    border-collapse: separate;
    overflow: hidden;
    width: 100%;
    }
    
    .biodata-table td {
    padding: 8px 10px;
    }

    .table-section {
      margin-top: 40px; background: #2d2f34;
      padding: 20px; border-radius: 12px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1); color: white;
    }
    table {
      width: 100%; border-collapse: collapse;
    }
    table th, table td {
      padding: 10px; border: 1px solid #444;
    }
    .table-section h3 { color: #fff; }
    table thead { background-color: #44484e; color: white; }
    table tbody tr { background-color: #3a3f47; color: white; }
    table tbody tr:nth-child(even) { background-color: #343942; }
    table tbody tr:hover { background-color: #50565e; }

    .dataTables_wrapper .dataTables_length,
    .dataTables_wrapper .dataTables_filter,
    .dataTables_wrapper .dataTables_info,
    .dataTables_wrapper .dataTables_paginate {
      color: white;
    }
    .dataTables_wrapper .dataTables_paginate .paginate_button {
      background-color: #444; color: white !important;
      border-radius: 4px;
    }
    .dataTables_wrapper .dataTables_paginate .paginate_button.current {
      background-color: #666 !important;
    }
    .dataTables_wrapper label,
    .dataTables_wrapper input,
    .dataTables_wrapper select {
      color: white;
    }
    .dataTables_wrapper .dataTables_info {
      color: white;
    }
    table.dataTable td,
    table.dataTable th {
      color: white;
    }
    table.dataTable thead th {
      background-color: #3a3f44;
    }
  </style>
</head>

<body>
  <div id="loader">
    <div class="spinner"></div>
  </div>

  <div class="sidebar" id="sidebar">
    <div class="brand">
      <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra">
      <h1>Tadika<br>Mesra</h1>
    </div>
    <nav>
        <a href="#" class="active"><i class="fa-solid fa-house"></i><span>Beranda</span></a>
        <a href="#"><i class="fa-solid fa-calendar-days"></i><span>Jadwal Mengajar</span></a>
        <a href="#"><i class="fa-solid fa-clipboard-check"></i><span>Rekap Nilai</span></a>
        <a href="#"><i class="fa-solid fa-user-check"></i><span>Daftar Siswa</span></a>
        <a href="#" onclick="return confirmLogout();"><i class="fa-solid fa-right-from-bracket"></i><span>Logout</span></a>
    </nav>
  </div>

  <div class="main" id="main">
    <div class="header">
      <div class="menu-icon" onclick="toggleSidebar()" style="cursor: pointer;"><i class="fa-solid fa-bars"></i> Dashboard</div>
      <div class="user"><i class="fa-solid fa-user"></i> <%= namaUser %></div>
    </div>

    <div class="content">
      <h2>Selamat Datang Kembali, <%= namaUser %></h2>

      <div style="display: flex; gap: 20px; margin-top: 20px; flex-wrap: wrap;">
        <div style="flex: 1; background: white; padding: 20px; border-radius: 12px;">
          <h3>Biodata</h3>
          <div style="display: flex; gap: 20px; align-items: center; margin-top: 15px;">
            <div style="flex: 0 0 200px;">
              <img class="foto-guru" src="<%= request.getContextPath() + "/assets/" + (foto != null && !foto.trim().isEmpty() ? foto : "profile-placeholder.png") %>" alt="Foto Guru" />
            </div>
            <div style="flex: 1; line-height: 1.8; font-size: 14px;">
              <table class="biodata-table">
                <tr><td colspan="2"><strong><%= namaUser %></strong></td></tr>
                <tr><td style="min-width: 100px;">NIP</td><td>: <%= nip %></td></tr>
                <tr><td>HP</td><td>: <%= hp %></td></tr>
                <tr><td>Email</td><td>: <%= email %></td></tr>
                <tr><td>Mata Pelajaran</td><td>: <%= mapel %></td></tr>
              </table>
            </div>
          </div>
        </div>

        <div style="flex: 1; background: white; padding: 20px; border-radius: 12px;">
          <h3 style="margin-bottom: 12px;">Jadwal Mengajar Hari Ini</h3>
          <%
            if (jadwalHariIni != null && !jadwalHariIni.isEmpty()) {
          %>
            <ul style="padding-left: 20px;">
              <% for (Jadwal j : jadwalHariIni) { %>
                <li>
                  <%= j.getJamMulai() %> - <%= j.getJamSelesai() %> :
                  <b><%= j.getNamaMapel() %></b>
                  (<i><%= j.getNamaKelas() %></i>)
                </li>
              <% } %>
            </ul>
          <%
            } else {
          %>
            <p style="color: gray;">Tidak ada jadwal hari ini.</p>
          <%
            }
          %>
        </div>
      </div>

      <div class="table-section" style="margin-top: 40px;">
        <h3>Pengumuman</h3>
        <table id="pengumumanTable" class="display">
          <thead><tr><th>No</th><th>Info</th></tr></thead>
          <tbody>
            <% int no = 1;
               for (Pengumuman p : pengumumanList) { %>
              <tr>
                <td><%= no++ %></td>
                <td><%= p.getIsi() %></td>
              </tr>
            <% } %>
          </tbody>
        </table>
      </div>
    </div>
  </div>

<script>
  window.addEventListener("beforeunload", function () {
    document.getElementById("loader").style.display = "flex";
  });

  $(document).ready(function () {
    $('#pengumumanTable').DataTable({
      pageLength: 5,
      lengthMenu: [5, 10, 25],
      language: {
        lengthMenu: "Tampilkan _MENU_ pengumuman",
        zeroRecords: "Tidak ada pengumuman ditemukan",
        info: "Menampilkan _START_ sampai _END_ dari _TOTAL_ pengumuman",
        infoEmpty: "Tidak ada data tersedia",
        infoFiltered: "(difilter dari _MAX_ total pengumuman)",
        search: "Cari:",
        paginate: {
          previous: "Sebelumnya",
          next: "Berikutnya"
        }
      }
    });
  });

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
