package com.tadikamesra.dao;

import com.tadikamesra.model.Siswa;
import com.tadikamesra.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SiswaDAO {

    public static Siswa getSiswaByUserId(int userId) {
        Siswa siswa = null;

        String sql = "SELECT s.siswa_id, s.user_id, s.nama, s.nisn, s.noHp AS no_hp, s.semester, s.email, " +
                     "s.kelas_id, k.nama_kelas AS kelas, g.nama AS wali_kelas, g.email AS email_wali " +
                     "FROM siswa s " +
                     "JOIN kelas k ON s.kelas_id = k.kelas_id " +
                     "LEFT JOIN guru g ON k.wali_kelas_id = g.guru_id " +
                     "WHERE s.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                siswa = new Siswa();
                siswa.setSiswaId(rs.getInt("siswa_id"));
                siswa.setUserId(rs.getInt("user_id"));
                siswa.setNama(rs.getString("nama"));
                siswa.setNisn(rs.getString("nisn"));
                siswa.setNoHp(rs.getString("no_hp"));
                siswa.setSemester(rs.getInt("semester"));
                siswa.setEmail(rs.getString("email"));
                siswa.setKelasId(rs.getInt("kelas_id")); // ✅ tambahkan ini
                siswa.setKelas(rs.getString("kelas"));
                siswa.setWaliKelas(rs.getString("wali_kelas"));
                siswa.setEmailWali(rs.getString("email_wali"));
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return siswa;
    }
    
    public static Siswa getDataPribadiByUserId(int userId) {
    Siswa siswa = null;

    String sql = "SELECT * FROM siswa WHERE user_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            siswa = new Siswa();
            siswa.setSiswaId(rs.getInt("siswa_id"));
            siswa.setUserId(rs.getInt("user_id"));
            siswa.setNama(rs.getString("nama"));
            siswa.setEmail(rs.getString("email"));
            siswa.setNoHp(rs.getString("noHp"));
            siswa.setTempatLahir(rs.getString("tempat_lahir"));
            siswa.setTanggalLahir(rs.getString("tanggal_lahir"));
            siswa.setAgama(rs.getString("agama"));
            siswa.setJenisKelamin(rs.getString("jenis_kelamin"));
            siswa.setStatusPernikahan(rs.getString("status_pernikahan"));
            siswa.setJumlahSaudara(rs.getInt("jumlah_saudara"));
            siswa.setAnakKe(rs.getInt("anak_ke"));
            siswa.setKewarganegaraan(rs.getString("kewarganegaraan"));
            siswa.setNoKK(rs.getString("no_kk"));
            siswa.setTipeIdentitas(rs.getString("tipe_identitas"));
            siswa.setNomorIdentitas(rs.getString("nomor_identitas"));
            siswa.setTelpRumah(rs.getString("telp_rumah"));

            // Alamat KTP
            siswa.setAlamatKtp(rs.getString("alamat_ktp"));
            siswa.setJalanKtp(rs.getString("jalan_ktp"));
            siswa.setProvinsiKtp(rs.getString("provinsi_ktp"));
            siswa.setKotaKtp(rs.getString("kota_ktp"));
            siswa.setKecamatanKtp(rs.getString("kecamatan_ktp"));
            siswa.setKelurahanKtp(rs.getString("kelurahan_ktp"));
            siswa.setRtKtp(rs.getInt("rt_ktp"));
            siswa.setRwKtp(rs.getInt("rw_ktp"));
            siswa.setKodeposKtp(rs.getString("kodepos_ktp"));

            // Alamat Domisili
            siswa.setAlamatDomisili(rs.getString("alamat_domisili"));
            siswa.setJalanDomisili(rs.getString("jalan_domisili"));
            siswa.setProvinsiDomisili(rs.getString("provinsi_domisili"));
            siswa.setKotaDomisili(rs.getString("kota_domisili"));
            siswa.setKecamatanDomisili(rs.getString("kecamatan_domisili"));
            siswa.setKelurahanDomisili(rs.getString("kelurahan_domisili"));
            siswa.setRtDomisili(rs.getInt("rt_domisili"));
            siswa.setRwDomisili(rs.getInt("rw_domisili"));
            siswa.setKodeposDomisili(rs.getString("kodepos_domisili"));
            siswa.setStatusTinggal(rs.getString("status_tinggal"));

            siswa.setFoto(rs.getString("foto"));
        }

        rs.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return siswa;
}

    
    public boolean updateProfilSiswa(Siswa siswa) {
    String sql = "UPDATE siswa SET " +
            "nama = ?, email = ?, noHp = ?, tempat_lahir = ?, tanggal_lahir = ?, agama = ?, " +
            "jenis_kelamin = ?, status_pernikahan = ?, jumlah_saudara = ?, anak_ke = ?, " +
            "kewarganegaraan = ?, no_kk = ?, tipe_identitas = ?, nomor_identitas = ?, telp_rumah = ?, " +

            "alamat_ktp = ?, jalan_ktp = ?, provinsi_ktp = ?, kota_ktp = ?, kecamatan_ktp = ?, kelurahan_ktp = ?, rt_ktp = ?, rw_ktp = ?, kodepos_ktp = ?, " +

            "alamat_domisili = ?, jalan_domisili = ?, provinsi_domisili = ?, kota_domisili = ?, kecamatan_domisili = ?, kelurahan_domisili = ?, rt_domisili = ?, rw_domisili = ?, kodepos_domisili = ?, status_tinggal = ?, " +

            "foto = ? WHERE siswa_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, siswa.getNama());
        stmt.setString(2, siswa.getEmail());
        stmt.setString(3, siswa.getNoHp());
        stmt.setString(4, siswa.getTempatLahir());
        stmt.setString(5, siswa.getTanggalLahir());
        stmt.setString(6, siswa.getAgama());
        stmt.setString(7, siswa.getJenisKelamin());
        stmt.setString(8, siswa.getStatusPernikahan());
        stmt.setInt(9, siswa.getJumlahSaudara());
        stmt.setInt(10, siswa.getAnakKe());
        stmt.setString(11, siswa.getKewarganegaraan());
        stmt.setString(12, siswa.getNoKK());
        stmt.setString(13, siswa.getTipeIdentitas());
        stmt.setString(14, siswa.getNomorIdentitas());
        stmt.setString(15, siswa.getTelpRumah());

        stmt.setString(16, siswa.getAlamatKtp());
        stmt.setString(17, siswa.getJalanKtp());
        stmt.setString(18, siswa.getProvinsiKtp());
        stmt.setString(19, siswa.getKotaKtp());
        stmt.setString(20, siswa.getKecamatanKtp());
        stmt.setString(21, siswa.getKelurahanKtp());
        stmt.setInt(22, siswa.getRtKtp());
        stmt.setInt(23, siswa.getRwKtp());
        stmt.setString(24, siswa.getKodeposKtp());

        stmt.setString(25, siswa.getAlamatDomisili());
        stmt.setString(26, siswa.getJalanDomisili());
        stmt.setString(27, siswa.getProvinsiDomisili());
        stmt.setString(28, siswa.getKotaDomisili());
        stmt.setString(29, siswa.getKecamatanDomisili());
        stmt.setString(30, siswa.getKelurahanDomisili());
        stmt.setInt(31, siswa.getRtDomisili());
        stmt.setInt(32, siswa.getRwDomisili());
        stmt.setString(33, siswa.getKodeposDomisili());
        stmt.setString(34, siswa.getStatusTinggal());

        stmt.setString(35, siswa.getFoto());
        stmt.setInt(36, siswa.getSiswaId());

        return stmt.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


}
