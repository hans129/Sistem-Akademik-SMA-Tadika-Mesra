<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tadikamesra.model.siswa" %>
<%@ page import="com.tadikamesra.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manajemen Siswa</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        h2 {
            color: #444;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
        }
        form {
            margin-top: 20px;
        }
        input[type=text], input[type=password] {
            padding: 8px;
            width: 100%;
            margin-bottom: 10px;
        }
        button {
            padding: 8px 15px;
            background-color: #4CAF50;
            border: none;
            color: white;
            cursor: pointer;
        }
        .delete-btn {
            background-color: #e74c3c;
        }
    </style>
</head>
<body>

<h2>Daftar Siswa</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Nama</th>
        <th>NIS</th>
        <th>Username</th>
        <th>Password</th>
        <th>Kelas</th>
        <th>Aksi</th>
    </tr>
    <%
        List<siswa> listSiswa = (List<siswa>) request.getAttribute("listSiswa");
        if (listSiswa != null) {
            for (siswa s : listSiswa) {
    %>
    <tr>
        <td><%= s.getIdSiswa() %></td>
        <td><%= s.getNama() %></td>
        <td><%= s.getNis() %></td>
        <td><%= s.getUsername() %></td>
        <td><%= s.getPassword() %></td>
        <td><%= s.getKelas() %></td>
        <td>
            <form action="SiswaController" method="get" style="display:inline;">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="<%= s.getIdSiswa() %>"/>
                <button type="submit" class="delete-btn">Hapus</button>
            </form>
        </td>
    </tr>
    <%      }
        }
    %>
</table>

<h2>Form Tambah / Edit Siswa</h2>
<form action="SiswaController" method="post">
    <input type="hidden" name="id_siswa" value=""/>
    <label>Nama:</label>
    <input type="text" name="nama" required/>

    <label>NIS:</label>
    <input type="text" name="nis" required/>

    <label>Username:</label>
    <input type="text" name="username" required/>

    <label>Password:</label>
    <input type="password" name="password" required/>

    <label>Kelas:</label>
    <input type="text" name="kelas" required/>

    <button type="submit">Simpan</button>
</form>

</body>
</html>
