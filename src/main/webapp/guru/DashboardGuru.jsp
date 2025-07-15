<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession sess = request.getSession(false);
    String username = "";
    if (sess != null) {
        username = (String) sess.getAttribute("username");
    } else {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Guru - SMA Tadika Mesra</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #0b0b5d;
            height: 100vh;
            display: flex;
        }
        .sidebar {
            width: 250px;
            background-color: #333941;
            color: #fff;
            padding-top: 20px;
            flex-shrink: 0;
            display: flex;
            flex-direction: column;
            height: 100vh;
        }
        .sidebar img {
            width: 60px;
            margin: 0 auto 10px;
        }
        .sidebar h2 {
            text-align: center;
            margin: 10px 0;
            font-size: 18px;
        }
        .sidebar a {
            display: block;
            color: #fff;
            padding: 12px 20px;
            text-decoration: none;
            transition: background 0.3s;
        }
        .sidebar a:hover, .sidebar a.active {
            background-color: #4a5162;
        }
        .content {
            flex-grow: 1;
            background-color: #f8f0f0;
            border-radius: 8px;
            margin: 20px;
            padding: 20px;
            overflow-y: auto;
        }
        .topbar {
            background-color: #fff;
            padding: 10px 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            border-bottom: 1px solid #ddd;
            border-radius: 8px 8px 0 0;
        }
        .topbar .left {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .topbar .left i {
            font-size: 24px;
            cursor: pointer;
        }
        .topbar .right {
            display: flex;
            align-items: center;
            gap: 10px;
            font-weight: bold;
            color: #555;
        }
        .content-inner {
            background: #fff;
            border-radius: 10px;
            min-height: 400px;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <img src="logo.png" alt="Logo SMA">
        <h2>Tadika Mesra</h2>
        <a href="dashboardGuru.jsp" class="active">&#8962; Dashboard</a>
        <a href="jadwalMengajar.jsp">&#128197; Jadwal Mengajar</a>
        <a href="inputNilai.jsp">&#9998; Input Nilai</a>
        <a href="daftarSiswa.jsp">&#128101; Daftar Siswa</a>
    </div>
    <div class="main">
        <div class="topbar">
            <div class="left">
                <i>&#9776;</i> <!-- icon burger -->
                <span>Dashboard</span>
            </div>
            <div class="right">
                &#128100; Prof. <%= username %>
                <a href="logout.jsp" title="Logout">&#x21bb;</a>
            </div>
        </div>
        <div class="content">
            <div class="content-inner">
                <h3>Selamat Datang di Dashboard Guru</h3>
                <p>Ini area konten. Silakan pilih menu di samping untuk mengakses fitur guru.</p>
            </div>
        </div>
    </div>
</body>
</html>
