<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="com.tadikamesra.model.Pengumuman" %> 
<%@ page import="com.tadikamesra.dao.PengumumanDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList, java.util.List" %>
<%@ page import="com.tadikamesra.util.DBConnection" %>
<%
    int userId = (Integer) session.getAttribute("userId");
    String namaUser = "User";

    int totalSiswa = 0;
    int totalGuru = 0;
    int totalMapel = 0;
    int totalPengunjung = 0;

    try (java.sql.Connection conn = DBConnection.getConnection()) {

        if (conn != null) {
            // Ambil nama user
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

            // Hitung total
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM siswa");
            if (rs.next()) totalSiswa = rs.getInt("total");
            rs.close();

            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM guru");
            if (rs.next()) totalGuru = rs.getInt("total");
            rs.close();

            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM mata_pelajaran");
            if (rs.next()) totalMapel = rs.getInt("total");
            rs.close();

            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM log_aktivitas");
            if (rs.next()) totalPengunjung = rs.getInt("total");
            rs.close();

            stmt.close();
        }

    } catch (Exception e) {
        out.println("\u2757ERROR koneksi / query: " + e.getMessage());
        e.printStackTrace();
    }

    List<Pengumuman> pengumumanList = PengumumanDAO.getRecentPengumuman();
    
    // Ambil aktivitas user
    List<String> aktivitasList = new ArrayList<>();
    try (java.sql.Connection conn = DBConnection.getConnection()) {
        String sqlAktivitas = "SELECT aktivitas, timestamp FROM log_aktivitas ORDER BY timestamp DESC LIMIT 10";
        java.sql.PreparedStatement psAktivitas = conn.prepareStatement(sqlAktivitas);
        java.sql.ResultSet rsAktivitas = psAktivitas.executeQuery();
        while (rsAktivitas.next()) {
            String aktivitas = rsAktivitas.getString("aktivitas");
            String waktu = rsAktivitas.getString("timestamp"); // <- diganti dari 'waktu' ke 'timestamp'
            aktivitasList.add(waktu + " - " + aktivitas);
        }
        rsAktivitas.close();
        psAktivitas.close();
    } catch (Exception e) {
        out.println("⚠ Error ambil log aktivitas: " + e.getMessage());
    }
%>

<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard Admin</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
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
    
    .sidebar.hidden {
    display: none;
    }

    .main.full {
    width: 100%;
    }

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
    .info-cards {
      display: flex; gap: 20px; flex-wrap: wrap; margin-bottom: 30px;
    }
    .info-card {
      flex: 1 1 200px; background: white; padding: 20px;
      border-radius: 12px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      text-align: center;
    }
    .info-card h2 { font-size: 14px; color: #555; margin-bottom: 10px; }
    .info-card p { font-size: 32px; font-weight: bold; color: #222; }

    .chart-activity-wrapper {
      display: flex; gap: 20px; flex-wrap: wrap;
    }
    .chart-section, .activity-section {
      flex: 1; background: white; border-radius: 12px;
      padding: 20px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      min-width: 300px;
    }
    .chart-section canvas {
      width: 100% !important; max-height: 280px;
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
      /* Warna teks dan input di tabel pengumuman */
  .dataTables_wrapper label,
  .dataTables_wrapper input,
  .dataTables_wrapper select {
    color: white;
  }


  .dataTables_wrapper .dataTables_paginate .paginate_button {
    color: white !important;
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
      <a href="${pageContext.request.contextPath}/admin/ManajemenPengumuman"><i class="fa-solid fa-envelope"></i><span>Manajemen Pengumuman</span></a>
      <a href="#"><i class="fa-solid fa-calendar-days"></i><span>Manajemen Jadwal</span></a>
      <a href="#"><i class="fa-solid fa-user-graduate"></i><span>Manajemen Siswa</span></a>
      <a href="${pageContext.request.contextPath}/admin/ManajemenGuru" class="nav-item"><i class="fa-solid fa-user-tie"></i> Manajemen Guru</a>
      <a href="#"><i class="fa-solid fa-file-lines"></i><span>Manajemen Laporan Akademik</span></a>
      <a href="#" onclick="return confirmLogout();" class="nav-item"><i class="fa-solid fa-right-from-bracket"></i><span>Logout</span></a>
    </nav>
  </div>
  <div class="main" id="main">
    <div class="header">
      <div class="menu-icon" onclick="toggleSidebar()" style="cursor: pointer;"><i class="fa-solid fa-bars"></i> Dashboard</div>
      <div class="user"><i class="fa-solid fa-user"></i> <%= namaUser %></div>
    </div>
    <div class="content">
      <h2>Selamat Datang Kembali, <%= namaUser %></h2>
      <div class="info-cards">
        <div class="info-card"><h2>Total Siswa</h2><p><%= totalSiswa %></p></div>
        <div class="info-card"><h2>Total Guru</h2><p><%= totalGuru %></p></div>
        <div class="info-card"><h2>Mata Pelajaran</h2><p><%= totalMapel %></p></div>
        <div class="info-card"><h2>Pengunjung</h2><p><%= totalPengunjung %></p></div>
      </div>
<div class="chart-activity-wrapper">
  <div class="chart-section">
    <h3>Aktivitas User</h3>
    <canvas id="userChart"></canvas>
  </div>
  <div class="activity-section">
    <h3>Aktivitas Terbaru</h3>
    <ul style="margin-top: 15px; list-style: disc; padding-left: 20px;">
      <% if (aktivitasList.isEmpty()) { %>
        <li>Tidak ada aktivitas terbaru.</li>
      <% } else {
           for (String aktivitas : aktivitasList) { %>
        <li><%= aktivitas %></li>
      <% }} %>
    </ul>
  </div>
</div>
      <div class="table-section">
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
  // Munculkan loader saat pindah halaman
  window.addEventListener("beforeunload", function () {
    document.getElementById("loader").style.display = "flex";
  });
</script>
  <script>
    const ctx = document.getElementById('userChart').getContext('2d');
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['Guru', 'Siswa', 'Admin'],
        datasets: [
          { label: '2020', data: [5, 123, 0], borderColor: '#3366CC', fill: false },
          { label: '2021', data: [5, 224, 0], borderColor: '#FF6633', fill: false },
          { label: '2022', data: [5, 234, 0], borderColor: '#00CC99', fill: false }
        ]
      },
      options: {
        responsive: true,
        plugins: { legend: { position: 'bottom' } },
        scales: { y: { beginAtZero: true } }
      }
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