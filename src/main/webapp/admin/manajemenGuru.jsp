<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tadikamesra.model.User" %>
<%@ page import="com.tadikamesra.model.Guru" %>
<%@ page import="java.util.List" %>

<%
    List<Guru> daftarGuru = (List<Guru>) request.getAttribute("daftarGuru");
    List<String> daftarMapel = (List<String>) request.getAttribute("daftarMapel");
    List<String> daftarKelas = (List<String>) request.getAttribute("daftarKelas");
    User user = (User) session.getAttribute("user");
    String username = user != null ? user.getUsername() : "Pengguna";
%>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Manajemen Guru</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap & Font Awesome -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body { margin: 0; font-family: 'Segoe UI', sans-serif; background: #f2f2f2; }
        .wrapper { display: flex; height: 100vh; overflow: hidden; }

        .sidebar {
            width: 240px;
            background: #2f3640;
            color: white;
            padding: 60px 20px 20px;
            position: fixed;
            top: 0; bottom: 0; left: 0;
            z-index: 1050;
            transition: all 0.3s ease;
        }

        .sidebar .brand {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            margin-bottom: 30px;
        }

        .sidebar .brand img {
            width: 40px; height: 40px;
        }

        .sidebar .brand h1 {
            font-size: 18px; font-weight: bold; line-height: 1.2;
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

        .sidebar nav a:hover, .sidebar nav a.active {
            background: #414b57;
        }

        .main {
            flex: 1;
            margin-left: 240px;
            padding: 20px;
            transition: all 0.3s ease;
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

        .form-section {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }

        .form-section h5 {
            margin-bottom: 20px;
        }

        table {
            background: white;
            border-radius: 10px;
            overflow: hidden;
        }

        .table th, .table td {
            vertical-align: middle;
        }

        .sidebar.hidden {
            margin-left: -240px;
        }

        .main.full {
            margin-left: 0;
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
        <div>
            <div class="brand">
                <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra">
                <h1>Tadika<br>Mesra</h1>
            </div>
            <nav>
                <a href="<%= request.getContextPath()%>/admin/beranda" class="nav-item active"><i class="fa-solid fa-house"></i> Beranda</a>
                <a href="#" class="nav-item"><i class="fa-solid fa-envelope"></i> Manajemen Pengumuman</a>
                <a href="#" class="nav-item"><i class="fa-solid fa-calendar-days"></i> Manajemen Jadwal</a>
                <a href="#" class="nav-item"><i class="fa-solid fa-user-graduate"></i> Manajemen Siswa</a>
                <a href="${pageContext.request.contextPath}/admin/manajemenguru" class="nav-item"><i class="fa-solid fa-user-tie"></i> Manajemen Guru</a>
                <a href="#" class="nav-item"><i class="fa-solid fa-file-lines"></i> Manajemen Laporan Akademik</a>
                <a href="#" class="nav-item"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
            </nav>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main" id="main">

        <!-- Header -->
        <div class="header">
            <div class="hamburger" onclick="toggleSidebar()">
                <i class="fa-solid fa-bars"></i>
            </div>
            <h4 class="mb-0"><i class="fa-solid fa-chalkboard-user"></i> Manajemen Guru</h4>
            <div>👤 <strong><%= username %></strong></div>
        </div>

        <!-- Content -->
        <div class="content">

            <!-- Form Tambah/Edit Guru -->
            <div class="form-section">
                <h5><i class="fa-solid fa-user-plus"></i> Tambah/Edit Data Guru</h5>
                <form action="${pageContext.request.contextPath}/admin/guru" method="post">
                    <input type="hidden" name="id" value="${guru.id}" />
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Nama</label>
                            <input type="text" name="nama" value="${guru.nama}" class="form-control" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">NIP</label>
                            <input type="text" name="nip" value="${guru.nip}" class="form-control" required />
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Username</label>
                            <input type="text" name="username" value="${guru.username}" class="form-control" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Password</label>
                            <input type="password" name="password" value="${guru.password}" class="form-control" required />
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Mata Pelajaran</label>
                            <select name="mata_pelajaran" class="form-select" required>
                                <option value="">-- Pilih Mapel --</option>
                                <c:forEach var="mapel" items="${daftarMapel}">
                                    <option value="${mapel}" <c:if test="${guru.mataPelajaran == mapel}">selected</c:if>>${mapel}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Wali Kelas</label>
                            <select name="wali_kelas" class="form-select">
                                <option value="">-- Bukan Wali Kelas --</option>
                                <c:forEach var="kelas" items="${daftarKelas}">
                                    <option value="${kelas}" <c:if test="${guru.waliKelas == kelas}">selected</c:if>>${kelas}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">
                        <i class="fa-solid fa-save"></i> Simpan
                    </button>
                </form>
            </div>

            <!-- Tabel Daftar Guru -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover align-middle">
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
                                    <!-- Tombol Hapus -->
                                    <form method="post" action="${pageContext.request.contextPath}/admin/guru" class="d-inline">
                                        <input type="hidden" name="_method" value="DELETE" />
                                        <input type="hidden" name="id" value="${g.id}" />
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Yakin ingin menghapus?');">
                                            <i class="fa-solid fa-trash"></i>
                                        </button>
                                    </form>
                                    <!-- Tombol Edit -->
                                    <form method="get" action="${pageContext.request.contextPath}/admin/guru/edit" class="d-inline">
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
            
        </div> <!-- End Content -->
    </div> <!-- End Main -->
</div> <!-- End Wrapper -->

<!-- JS Bootstrap & Sidebar Toggle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function toggleSidebar() {
        const sidebar = document.getElementById('sidebar');
        const main = document.getElementById('main');
        sidebar.classList.toggle('hidden');
        main.classList.toggle('full');
    }
</script>
</body>
</html>
