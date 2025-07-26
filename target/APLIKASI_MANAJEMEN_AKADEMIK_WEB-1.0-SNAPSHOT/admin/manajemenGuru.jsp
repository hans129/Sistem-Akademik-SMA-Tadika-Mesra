<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.tadikamesra.model.User" %>
<%@ page import="com.tadikamesra.model.Guru" %>
<%@ page import="java.util.List" %>

<% 
    List<Guru> daftarGuru = (List<Guru>) request.getAttribute("daftarGuru"); 
%>
<%
    User user = (User) session.getAttribute("user");
    String username = user != null ? user.getUsername() : "Pengguna";
%>

<%! 
    public String escapeJS(String s) {
        return s == null ? "" : s.replace("'", "\\'");
    }
%>

<% if (request.getAttribute("error") != null) { %>
  <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
<% } %>


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
<body class="p-5">
    
    <div class="wrapper">
        <!-- Sidebar -->
        <div class="sidebar" id="sidebar">
            <div>
                <div class="brand">
                    <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra">
                    <h1>Tadika<br>Mesra</h1>
                </div>
                <nav>
                    <a href="<%= request.getContextPath()%>/admin/beranda"><i class="fa-solid fa-house"></i>Beranda</a>
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

<!-- Form Tambah/Edit Guru -->
<form action="guru" method="post" class="mb-5" id="formGuru">
  <input type="hidden" name="action" id="actionGuru" value="add">
  <input type="hidden" name="id" id="idGuru">
  <div class="row g-3">
    <div class="col-md-4">
      <label>Nama</label>
      <input type="text" name="nama" id="nama" class="form-control" required>
    </div>
    <div class="col-md-4">
      <label>NIP</label>
      <input type="text" name="nip" id="nip" class="form-control" required>
    </div>
    <div class="col-md-4">
      <label>Username</label>
      <input type="text" name="username" id="username" class="form-control" required>
    </div>
    <div class="col-md-4">
      <label>Password</label>
      <input type="password" name="password" id="password" class="form-control">
    </div>
    <div class="col-md-4">
      <label>Mata Pelajaran</label>
      <input type="text" name="mata_pelajaran" id="mata_pelajaran" class="form-control" required>
    </div>
    <div class="col-md-4">
      <label>Wali Kelas</label>
      <input type="text" name="wali_kelas" id="wali_kelas" class="form-control">
    </div>
    <div class="col-12">
      <button type="submit" class="btn btn-primary">Simpan Guru</button>
    </div>
  </div>
</form>

<!-- Tabel Data Guru -->
<table class="table table-bordered table-striped">
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
    <% if (daftarGuru != null && !daftarGuru.isEmpty()) {
         for (Guru g : daftarGuru) { %>
      <tr>
        <td><%= g.getId() %></td>
        <td><%= g.getNama() %></td>
        <td><%= g.getNip() %></td>
        <td><%= g.getUsername() %></td>
        <td><%= g.getMataPelajaran() %></td>
        <td><%= g.getWaliKelas() %></td>
        <td>
          <button type="button" class="btn btn-sm btn-warning"
            onclick="editGuru(<%= g.getId() %>, '<%= escapeJS(g.getNama()) %>', '<%= escapeJS(g.getNip()) %>', '<%= escapeJS(g.getUsername()) %>', '<%= escapeJS(g.getMataPelajaran()) %>', '<%= escapeJS(g.getWaliKelas()) %>')">Edit</button>

          <form action="guru" method="post" style="display:inline;" onsubmit="return confirm('Yakin ingin menghapus?')">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="<%= g.getId() %>">
            <button type="submit" class="btn btn-sm btn-danger">Hapus</button>
          </form>
        </td>
      </tr>
    <% } 
       } else { %>
      <tr>
        <td colspan="7" class="text-center">Belum ada data guru.</td>
      </tr>
    <% } %>
  </tbody>
</table>

<!-- Script Edit -->
<script>
function editGuru(id, nama, nip, username, mataPelajaran, waliKelas) {
  document.getElementById('idGuru').value = id;
  document.getElementById('nama').value = nama;
  document.getElementById('nip').value = nip;
  document.getElementById('username').value = username;
  document.getElementById('mata_pelajaran').value = mataPelajaran;
  document.getElementById('wali_kelas').value = waliKelas;
  document.getElementById('password').value = '';
  document.getElementById('actionGuru').value = 'edit';
}

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
