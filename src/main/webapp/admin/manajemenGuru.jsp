<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.tadikamesra.model.Guru" %>
<html>
<head>
    <title>Manajemen Guru</title>
</head>
<body>
    <h2>Manajemen Guru</h2>
    <form method="post" action="manajemenGuru">
        Nama: <input type="text" name="nama"><br>
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        Mata Pelajaran: <input type="text" name="mataPelajaran"><br>
        Wali Kelas: <input type="text" name="waliKelas"><br>
        <input type="submit" value="Simpan">
    </form>
    <hr>
    <h3>Data Guru</h3>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nama</th>
            <th>Username</th>
            <th>Password</th>
            <th>Mata Pelajaran</th>
            <th>Wali Kelas</th>
        </tr>
        <%
            List<Guru> guruList = (List<Guru>) request.getAttribute("guruList");
            if (guruList != null) {
                for (Guru g : guruList) {
        %>
        <tr>
            <td><%= g.getIdGuru() %></td>
            <td><%= g.getNama() %></td>
            <td><%= g.getUsername() %></td>
            <td><%= g.getPassword() %></td>
            <td><%= g.getMataPelajaran() %></td>
            <td><%= g.getWaliKelas() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
