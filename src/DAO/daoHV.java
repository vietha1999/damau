/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import CLASS.hocVien;
import helPer.JDBCHP;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H2
 */
public class daoHV {
     public void insert(hocVien model) {
        String sql = "INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
        JDBCHP.executeUpdate(sql, model.getMaKH(), model.getMaNH(), model.getDiem());
    }

    public void update(hocVien model) {
        String sql = "UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
        JDBCHP.executeUpdate(sql, model.getMaKH(), model.getMaNH(), model.getDiem(), model.getMaHV());
    }

    public void delete(Integer MaHV) {
        String sql = "DELETE FROM HocVien WHERE MaHV=?";
        JDBCHP.executeUpdate(sql, MaHV);
    }

    public List<hocVien> select() {
        String sql = "SELECT * FROM HocVien";
        return select(sql);
    }

    public hocVien findById(Integer mahv) {
        String sql = "SELECT * FROM HocVien WHERE MaHV=?";
        List<hocVien> list = select(sql, mahv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<hocVien> select(String sql, Object... args) {
        List<hocVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHP.executeQuery(sql, args);
                while (rs.next()) {
                    hocVien model = readFromResultSet(rs);
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

    private hocVien readFromResultSet(ResultSet rs) throws SQLException {
        hocVien model = new hocVien();
        model.setMaHV(rs.getInt("MaHV"));
        model.setMaKH(rs.getInt("KH"));
        model.setMaNH(rs.getString("MaNH"));
        model.setDiem(rs.getDouble("Diem"));
        return model;
    }
}
