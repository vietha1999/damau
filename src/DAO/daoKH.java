/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import CLASS.khoaHoc;
import helPer.JDBCHP;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H2
 */
public class daoKH {
     public void insert(khoaHoc model) {
        String sql = "INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?)";
        JDBCHP.executeUpdate(sql, model.getMaCD(), model.getHocPhi(), model.getThoiLuong(), model.getNgayKG(), model.getGhiChu(), model.getMaNV());
    }

    public void update(khoaHoc model) {
        String sql = "UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? WHERE MaKH=?";
        JDBCHP.executeUpdate(sql,
                model.getMaCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getNgayKG(),
                model.getGhiChu(),
                model.getMaNV(),
                model.getMaKH()
        );
    }

    public void delete(Integer MaKH) {
        String sql = "DELETE FROM KhoaHoc WHERE MaKH=?";
        JDBCHP.executeUpdate(sql, MaKH);
    }

    public List<khoaHoc> select() {
        String sql = "SELECT * FROM KhoaHoc";
        return select(sql);
    }

    public khoaHoc findById(Integer makh) {
        String sql = "SELECT * FROM KhoaHoc WHERE MaKH=?";
        List<khoaHoc> list = select(sql, makh);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<khoaHoc> select(String sql, Object... args) {
        List<khoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHP.executeQuery(sql, args);
                while (rs.next()) {
                    khoaHoc model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private khoaHoc readFromResultSet(ResultSet rs) throws SQLException {
        khoaHoc model = new khoaHoc();
        model.setMaKH(rs.getInt("MaKH"));
        model.setHocPhi(rs.getDouble("HocPhi"));
        model.setThoiLuong(rs.getInt("ThoiLuong"));
        model.setNgayKG(rs.getDate("NgayKG"));
        model.setGhiChu(rs.getString("GhiChu"));

        model.setMaNV(rs.getString("MaNV"));
        model.setNgayTao(rs.getDate("NgayTao"));
        model.setMaCD(rs.getString("MaCD"));
        return model;
    }
}
