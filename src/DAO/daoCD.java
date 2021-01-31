/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import CLASS.chuyenDe;
import helPer.JDBCHP;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H2
 */
public class daoCD {
       public void insert(chuyenDe model) {
        String sql = "INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
        JDBCHP.executeUpdate(sql, model.getMaCD(), model.getTenCD(), model.getHocPhi(), model.getThoiLuong(), model.getHinh(), model.getMoTa());
    }

    public void update(chuyenDe model) {
        String sql = "UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
        JDBCHP.executeUpdate(sql, model.getTenCD(), model.getHocPhi(), model.getThoiLuong(), model.getHinh(), model.getMoTa(), model.getMaCD());
    }

    public void delete(String MaCD) {
        String sql = "DELETE FROM ChuyenDe WHERE MaCD=?";
        JDBCHP.executeUpdate(sql, MaCD);
    }

    public List<chuyenDe> select() {
        String sql = "SELECT * FROM ChuyenDe";
        return select(sql);
    }

    public chuyenDe findById(String macd) {
        String sql = "SELECT * FROM ChuyenDe WHERE MaCD=?";
        List<chuyenDe> list = select(sql, macd);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<chuyenDe> select(String sql, Object... args) {
        List<chuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHP.executeQuery(sql, args);

                while (rs.next()) {
                    chuyenDe model = readFromResultSet(rs);
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

    private chuyenDe readFromResultSet(ResultSet rs) throws SQLException {
        chuyenDe model = new chuyenDe();
        model.setMaCD(rs.getString("MaCD"));
        model.setHinh(rs.getString("Hinh"));
        model.setHocPhi(rs.getDouble("HocPhi"));
        model.setMoTa(rs.getString("MoTa"));
        model.setTenCD(rs.getString("TenCD"));
        model.setThoiLuong(rs.getInt("ThoiLuong"));
        return model;
    }
}
