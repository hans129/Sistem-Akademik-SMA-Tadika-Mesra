<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tadikamesra.model.Siswa" %>
<%@ page import="com.tadikamesra.dao.SiswaDAO" %>

<%
    Siswa siswaSession = (Siswa) session.getAttribute("siswa");

    if (siswaSession == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Ambil data lengkap dari database pakai userId dari session
    Siswa siswa = SiswaDAO.getDataPribadiByUserId(siswaSession.getUserId());

    if (siswa == null) {
        out.println("Data siswa tidak ditemukan.");
        return;
    }

    // Ambil nama dan foto
    String namaUser = siswa.getNama();
    String foto = siswa.getFoto();

    // Kalau path-nya "foto_siswa/namafile.jpg", ambil hanya nama file-nya
    if (foto != null && foto.startsWith("foto_siswa/")) {
        foto = foto.substring("foto_siswa/".length()); // hasilnya: "namafile.jpg"
    }
%>


<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Pemutahiran Data Siswa</title>
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
    html {
    font-size: 16px;
    }
    body {
    font-size: 1rem;
    }

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
    .header {
      height: 60px; background: #fff;
      display: flex; justify-content: space-between;
      align-items: center; padding: 0 20px;
      border-bottom: 1px solid #ccc;
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
    .foto-siswa {
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
    
    .btn-simpan {
    background-color: #28a745;         /* Warna hijau */
    color: white;                      /* Warna teks */
    padding: 10px 20px;                /* Ruang dalam */
    font-size: 16px;
    font-weight: bold;
    border: none;
    border-radius: 8px;                /* Sudut tombol */
    cursor: pointer;
    transition: background-color 0.3s ease;
    box-shadow: 0 2px 6px rgba(0,0,0,0.2);
}

.btn-simpan:hover {
    background-color: #218838;         /* Hijau lebih gelap saat hover */
}
  </style>
</head>

<body>
  <!-- Loader -->
  <div id="loader">
    <div class="spinner"></div>
  </div>

  <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
      <div class="brand">
        <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra">
        <h1>Tadika<br>Mesra</h1>
      </div>
    <nav>
      <a href="${pageContext.request.contextPath}/siswa/BerandaSiswa"><i class="fa-solid fa-house me-2"></i> Beranda</a>
      <a href="<%= request.getContextPath() %>/siswa/DataPribadi.jsp" class="active"><i class="fa-solid fa-id-card"></i> Data Pribadi</a>
      <a href="#" onclick="return confirmLogout();"><i class="fa-solid fa-right-from-bracket"></i><span>Logout</span></a>
    </nav>
  </div>

  <!-- Main -->
  <div class="main" id="main">
    <div class="header">
      <div class="menu-icon" onclick="toggleSidebar()" style="cursor: pointer;"><i class="fa-solid fa-bars"></i> Dashboard</div>
      <div class="user"><i class="fa-solid fa-user"></i> <%= namaUser %></div>
    </div>

    <div class="content">
      <h3>Data Pribadi Siswa</h3>

      <form action="UpdateProfilSiswaServlet" method="post" enctype="multipart/form-data">
        <!-- Biodata -->
        <div class="biodata-section">
          <div class="biodata-wrapper" style="display: flex; gap: 10px; flex-wrap: wrap;">
            <!-- Foto -->
            <div class="foto-container" style="flex: 1; min-width: 200px;">
              <img class="foto-siswa"
                src="<%= request.getContextPath() + "/assets/" + (foto != null && !foto.trim().isEmpty() ? foto : "profile-placeholder.png") %>"
                alt="Foto Siswa" />
              
              <label for="foto">Foto (PNG/JPG/JPEG maks. 1MB)</label><br>
              <input type="file" name="foto" accept="image/png, image/jpeg" />
            </div>

            <!-- Biodata Table -->
            <div class="biodata-container" style="flex: 2;">
              <table class="biodata-table">
                <tr><td>Nama Lengkap</td><td><input type="text" name="nama" value="<%= siswa.getNama() %>" required /></td></tr>
                <tr><td>NISN</td><td><input type="text" name="nisn" value="<%= siswa.getNisn() %>" readonly /></td></tr>
                <tr><td>Email</td><td><input type="email" name="email" value="<%= siswa.getEmail() %>" /></td></tr>
                <tr><td>Agama</td><td><input type="text" name="agama" value="<%= siswa.getAgama() %>" /></td></tr>
                <tr><td>Jenis Kelamin</td><td><input type="text" name="jenisKelamin" value="<%= siswa.getJenisKelamin() %>" /></td></tr>
                <tr><td>Status Pernikahan</td><td><input type="text" name="statusPernikahan" value="<%= siswa.getStatusPernikahan() %>" /></td></tr>
                <tr><td>Jumlah Saudara</td><td><input type="number" name="jumlahSaudara" value="<%= siswa.getJumlahSaudara() %>" /></td></tr>
                <tr><td>Anak ke-</td><td><input type="number" name="anakKe" value="<%= siswa.getAnakKe() %>" /></td></tr>
                <tr><td>Tempat Lahir</td><td><input type="text" name="tempatLahir" value="<%= siswa.getTempatLahir() %>" /></td></tr>
                <tr><td>Tanggal Lahir</td><td><input type="date" name="tanggalLahir" value="<%= siswa.getTanggalLahir() %>" /></td></tr>
                <tr><td>Kewarganegaraan</td><td><input type="text" name="kewarganegaraan" value="<%= siswa.getKewarganegaraan() %>" /></td></tr>
                <tr><td>No KK</td><td><input type="text" name="noKK" value="<%= siswa.getNoKK() %>" /></td></tr>
                <tr><td>Tipe Identitas</td><td><input type="text" name="tipeIdentitas" value="<%= siswa.getTipeIdentitas() %>" /></td></tr>
                <tr><td>Nomor Identitas</td><td><input type="text" name="nomorIdentitas" value="<%= siswa.getNomorIdentitas() %>" /></td></tr>
                <tr><td>No Telp Rumah</td><td><input type="text" name="telpRumah" value="<%= siswa.getTelpRumah() %>" /></td></tr>
                <tr><td>No HP / WhatsApp</td><td><input type="text" name="noHp" value="<%= siswa.getNoHp() %>" /></td></tr>
              </table>
            </div>
          </div>
        </div>

        <!-- Alamat KTP -->
        <div class="table-section">
          <h3>Alamat Sesuai KTP</h3>
          <table>
            <tr><td>Alamat Tinggal</td><td><input type="text" name="alamatKtp" value="<%= siswa.getAlamatKtp() %>" /></td></tr>
            <tr><td>Nama Jalan</td><td><input type="text" name="jalanKtp" value="<%= siswa.getJalanKtp() %>" /></td></tr>
            <tr><td>Provinsi</td><td><input type="text" name="provinsiKtp" value="<%= siswa.getProvinsiKtp() %>" /></td></tr>
            <tr><td>Kabupaten/Kota</td><td><input type="text" name="kotaKtp" value="<%= siswa.getKotaKtp() %>" /></td></tr>
            <tr><td>Kecamatan</td><td><input type="text" name="kecamatanKtp" value="<%= siswa.getKecamatanKtp() %>" /></td></tr>
            <tr><td>Kelurahan</td><td><input type="text" name="kelurahanKtp" value="<%= siswa.getKelurahanKtp() %>" /></td></tr>
            <tr><td>RT</td><td><input type="number" name="rtKtp" value="<%= siswa.getRtKtp() %>" /></td></tr>
            <tr><td>RW</td><td><input type="number" name="rwKtp" value="<%= siswa.getRwKtp() %>" /></td></tr>
            <tr><td>Kode Pos</td><td><input type="text" name="kodeposKtp" value="<%= siswa.getKodeposKtp() %>" /></td></tr>
          </table>

          <h3>Alamat Domisili Saat Ini</h3>
          <table>
            <tr><td>Alamat Tinggal</td><td><input type="text" name="alamatDomisili" value="<%= siswa.getAlamatDomisili() %>" /></td></tr>
            <tr><td>Nama Jalan</td><td><input type="text" name="jalanDomisili" value="<%= siswa.getJalanDomisili() %>" /></td></tr>
            <tr><td>Provinsi</td><td><input type="text" name="provinsiDomisili" value="<%= siswa.getProvinsiDomisili() %>" /></td></tr>
            <tr><td>Kabupaten/Kota</td><td><input type="text" name="kotaDomisili" value="<%= siswa.getKotaDomisili() %>" /></td></tr>
            <tr><td>Kecamatan</td><td><input type="text" name="kecamatanDomisili" value="<%= siswa.getKecamatanDomisili() %>" /></td></tr>
            <tr><td>Kelurahan</td><td><input type="text" name="kelurahanDomisili" value="<%= siswa.getKelurahanDomisili() %>" /></td></tr>
            <tr><td>RT</td><td><input type="number" name="rtDomisili" value="<%= siswa.getRtDomisili() %>" /></td></tr>
            <tr><td>RW</td><td><input type="number" name="rwDomisili" value="<%= siswa.getRwDomisili() %>" /></td></tr>
            <tr><td>Kode Pos</td><td><input type="text" name="kodeposDomisili" value="<%= siswa.getKodeposDomisili() %>" /></td></tr>
            <tr><td>Status Tinggal</td><td><input type="text" name="statusTinggal" value="<%= siswa.getStatusTinggal() %>" /></td></tr>
          </table>
        </div>

        <br>
        <div style="text-align: right; margin-top: 20px;">
            <button type="submit" class="btn-simpan">💾 Simpan Data</button>
        </div>

      </form>
    </div>
  </div>

  <!-- JS loader jika perlu -->
  <script>
    window.addEventListener("load", function() {
      document.getElementById("loader").style.display = "none";
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

