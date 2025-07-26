<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.tadikamesra.model.Guru" %>
<%@ page import="java.util.List" %>
<%
    List<Guru> daftarGuru = (List<Guru>) request.getAttribute("daftarGuru");

    // Escape JS string helper
    String escapeJS(String s) {
        return s == null ? "" : s.replace("'", "\\'");
    }
%>

<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <title>Manajemen Guru</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-5">

<h2 class="mb-4">Manajemen Guru</h2>

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
      <input type="text" name="wali_kelas" id="wali_kelas" class="form-control" required>
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
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
