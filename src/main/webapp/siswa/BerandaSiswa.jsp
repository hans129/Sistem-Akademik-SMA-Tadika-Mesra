<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Siswa</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f4f4;
        }

        .wrapper {
            display: flex;
            height: 100vh;
            overflow: hidden;
        }

        .sidebar {
            width: 250px;
            background-color: #2C2F33;
            color: white;
            transition: transform 0.3s ease;
        }

        .sidebar.hide {
            transform: translateX(-250px);
        }

        .sidebar .brand {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 20px;
        }

        .sidebar .brand img {
            width: 40px;
            height: 40px;
        }

        .sidebar .brand h1 {
            font-size: 18px;
            line-height: 1.2;
        }

        .sidebar nav {
            display: flex;
            flex-direction: column;
            padding: 0 10px;
        }

        .sidebar nav a {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 16px;
            color: white;
            border-radius: 10px;
            margin: 5px 0;
            text-decoration: none;
            transition: background 0.3s;
        }

        .sidebar nav a:hover,
        .sidebar nav a.active {
            background-color: #40464D;
        }

        .sidebar nav a i {
            width: 20px;
            text-align: center;
        }

        .main {
            flex-grow: 1;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }

        .header {
            height: 60px;
            background: #ffffff;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 20px;
            border-bottom: 1px solid #ddd;
        }

        .header .menu-toggle {
            font-size: 20px;
            cursor: pointer;
            color: #333;
        }

        .header .title {
            font-weight: 600;
            font-size: 18px;
        }

        .header .user {
            display: flex;
            align-items: center;
            gap: 8px;
            color: #666;
        }

        .content {
            flex-grow: 1;
            padding: 30px;
            overflow-y: auto;
            background-color: #f4f4f4;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        /* Responsive */
        @media (max-width: 768px) {
            .sidebar {
                position: fixed;
                height: 100%;
                z-index: 10;
                left: 0;
                top: 0;
            }

            .sidebar.hide {
                transform: translateX(-250px);
            }

            .overlay {
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0,0,0,0.5);
                display: none;
                z-index: 5;
            }

            .overlay.show {
                display: block;
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
            <a href="#" class="active"><i class="fa-solid fa-house"></i> Beranda</a>
            <a href="#"><i class="fa-solid fa-calendar-days"></i> Jadwal Pelajaran</a>
            <a href="#"><i class="fa-solid fa-star"></i> Nilai</a>
            <a href="#"><i class="fa-solid fa-clipboard-user"></i> Absensi</a>
            <a href="#" style="opacity: 0.5; pointer-events: none;"><i class="fa-solid fa-lock"></i> Administrasi akademik</a>
        </nav>
    </div>

    <!-- Main -->
    <div class="main">
        <div class="header">
            <div class="menu-toggle" id="toggleBtn"><i class="fa-solid fa-bars"></i></div>
            <div class="title">Dashboard Siswa</div>
            <div class="user"><i class="fa-solid fa-user"></i> Ujang Resing</div>
        </div>
        <div class="content">
            <div class="card">
                <h2>Selamat datang di Dashboard Siswa</h2>
                <p>Ini adalah halaman utama siswa.</p>
            </div>
        </div>
    </div>
</div>

<div class="overlay" id="overlay"></div>

<script>
    const toggleBtn = document.getElementById("toggleBtn");
    const sidebar = document.getElementById("sidebar");
    const overlay = document.getElementById("overlay");

    toggleBtn.addEventListener("click", () => {
        sidebar.classList.toggle("hide");
        overlay.classList.toggle("show");
    });

    overlay.addEventListener("click", () => {
        sidebar.classList.add("hide");
        overlay.classList.remove("show");
    });
</script>
</body>
</html>
