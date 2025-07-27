<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tadikamesra.model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Guru</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .sidebar {
            height: 100vh;
            background-color: #343a40;
            color: white;
        }
        .sidebar .nav-link {
            color: white;
        }
        .sidebar .nav-link.active, .sidebar .nav-link:hover {
            background-color: #495057;
        }
        .header {
            height: 60px;
            background-color: white;
            border-bottom: 1px solid #dee2e6;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 1rem;
        }
        .hamburger {
            font-size: 1.2rem;
            cursor: pointer;
        }
        .username-dropdown {
            position: relative;
        }
    </style>
</head>
<body>
<div class="d-flex">
    <!-- Sidebar -->
    <div class="sidebar p-3" style="width: 240px;">
        <div class="d-flex align-items-center mb-4">
            <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo" width="40" height="40" class="me-2">
            <div>
                <strong>SMA</strong><br>Tadika Mesra
            </div>
        </div>
        <ul class="nav nav-pills flex-column">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/dashboardGuru" class="nav-link active">
                    <i class="fa-solid fa-house me-2"></i> Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link">
                    <i class="fa-solid fa-calendar-days me-2"></i> Jadwal Mengajar
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link">
                    <i class="fa-solid fa-file-lines me-2"></i> Rekap Nilai
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link">
                    <i class="fa-solid fa-users me-2"></i> Daftar Siswa
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/logout" class="nav-link">
                    <i class="fa-solid fa-right-from-bracket me-2"></i> Logout
                </a>
            </li>
        </ul>
    </div>

    <!-- Main Content -->
    <div class="flex-grow-1">
        <!-- Header -->
        <div class="header">
            <div class="d-flex align-items-center">
                <i class="fa-solid fa-bars me-3 hamburger"></i>
                <h5 class="mb-0">Dashboard Guru</h5>
            </div>
            <div class="dropdown">
                <a class="text-decoration-none text-dark dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                    <i class="fa-solid fa-user me-1"></i> <%= user != null ? user.getUsername() : "Guru" %>
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><a class="dropdown-item" href="#">Profil</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </ul>
            </div>
        </div>

        <!-- Content -->
        <div class="container-fluid p-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Selamat datang di Dashboard Guru</h5>
                    <p class="card-text">Ini adalah halaman utama guru. Silakan pilih menu di samping untuk mulai menggunakan sistem.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
