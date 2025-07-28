package com.tadikamesra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tadikamesra.model.Pengumuman;
import com.tadikamesra.util.DBConnection;

/**
 *
 * @author hp
 */
public class PengumumanDAO {

    public static List<Pengumuman> getRecentPengumuman() {
        List<Pengumuman> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT isi FROM pengumuman ORDER BY tanggal DESC LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pengumuman p = new Pengumuman();
                p.setIsi(rs.getString("isi"));
                list.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace(); // Bisa ganti dengan logging
        }

        return list;
    }
}
