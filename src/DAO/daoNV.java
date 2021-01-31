/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import CLASS.nhanVien;
import helPer.JDBCHP;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author H2
 */
public class daoNV {
      public void insert(nhanVien nv) {
        String sql = "INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro) VALUES (?, ?, ?, ?)";
        JDBCHP.executeUpdate(sql, nv.getMaNV(), nv.getMatKhau(), nv.getHoTen(), nv.getVaiTro());
    }

    public void update(nhanVien nv) {
        String sql = "UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
        JDBCHP.executeUpdate(sql, nv.getMatKhau(), nv.getHoTen(), nv.getVaiTro(), nv.getMaNV());

    }

    public void delete(String MaNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        JDBCHP.executeUpdate(sql, MaNV);
    }

    public List<nhanVien> select() {
        String sql = "SELECT * FROM NhanVien";
        return (List<nhanVien>) select(sql);
    }

    public nhanVien findById(String manv) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<nhanVien> list = (List<nhanVien>) select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<nhanVien> select(String sql, Object... args) {
        List<nhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHP.executeQuery(sql, args);
                while (rs.next()) {
                    nhanVien nv = readFromResultSet(rs);
                    list.add(nv);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private nhanVien readFromResultSet(ResultSet rs) throws SQLException {
        nhanVien nv = new nhanVien();
        nv.setMaNV(rs.getString("MaNV"));
        nv.setMatKhau(rs.getString("MatKhau"));
        nv.setHoTen(rs.getString("HoTen"));
        nv.setVaiTro(rs.getBoolean("VaiTro"));
        return nv;
    }
}
