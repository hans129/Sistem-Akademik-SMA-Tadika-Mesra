<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard Guru</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', sans-serif; }
    body { display: flex; height: 100vh; background: #f9f3f3; }

    /* Sidebar */
    .sidebar {
      width: 240px;
      background: #353A40;
      color: white;
      padding: 60px 20px 20px; /* padding top ditambah agar sejajar header */
      display: flex;
      flex-direction: column;
      justify-content: space-between;
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

    /* Header */
    .main {
      flex: 1;
      display: flex;
      flex-direction: column;
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
    .header .menu-icon {
      font-size: 20px;
      color: #777;
    }
    .header .user {
      display: flex;
      align-items: center;
      gap: 8px;
      color: #777;
      font-weight: 500;
    }

    /* Content */
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
  </style>
</head>
<body>
  <div class="sidebar">
    <div>
      <div class="brand">
        <img src="${pageContext.request.contextPath}/assets/LogoSTM.png" alt="Logo Tadika Mesra">
        <h1>Tadika<br>Mesra</h1>
      </div>
      <nav>
            <a href="#" class="nav-item active"><i class="fa-solid fa-house"></i> Beranda</a>
            <a href="#" class="nav-item"><i class="fa-solid fa-calendar-days"></i> Jadwal Mengajar</a>
            <a href="#" class="nav-item"><i class="fa-solid fa-file-lines"></i> Rekap Nilai</a>
            <a href="#" class="nav-item"><i class="fa-solid fa-users"></i> Daftar Siswa</a>
            <a href="#" class="nav-item"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
      </nav>
    </div>
  </div>
  <div class="main">
    <div class="header">
      <div class="menu-icon"><i class="fa-solid fa-bars"></i> Beranda </div>
      <div class="user"><i class="fa-solid fa-user"></i> Prof. Jhon Sunandar</div>
    </div>
    <div class="content">
      <div class="card"></div>
    </div>
  </div>
  <script>
  const navItems = document.querySelectorAll('.sidebar nav .nav-item');

    navItems.forEach(item => {
        item.addEventListener('click', () => {
           navItems.forEach(i => i.classList.remove('active'));
           item.classList.add('active');
        });
    });
  </script>
</body>
</html>
