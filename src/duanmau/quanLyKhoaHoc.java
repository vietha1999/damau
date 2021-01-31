/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duanmau;

import CLASS.chuyenDe;
import CLASS.khoaHoc;
import DAO.daoCD;
import DAO.daoKH;
import java.awt.HeadlessException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author H2
 */
public class quanLyKhoaHoc extends javax.swing.JFrame {
DAO.daoKH dao = new daoKH();
DAO.daoCD cddao =new daoCD();
int index=0;
public Integer a;
boolean flag =false;
    /**
     * Creates new form quanLyKhoaHoc
     */
    public quanLyKhoaHoc() {
        initComponents();
        setIconImage(helPer.ShareHP.APP_ICON);
        setLocationRelativeTo(null);
        if (helPer.ShareHP.USER != null) {
            this.fillComboBox();
            this.load();
            this.clear();
            this.selectComboBox();
            jTextField1.setText("");
            this.setStatus(true);
        } else {
            helPer.DialogHP.alert(this, "Vui lòng đăng nhập");
            this.jTabbedPane1.removeAll();
        }
        jTextField4.setEditable(false);
    }
 void load() {
        jButton1.setEnabled(false);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try {
            List<CLASS.khoaHoc> list = dao.select();
            for (CLASS.khoaHoc kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getMaCD(),
                    kh.getThoiLuong(),
                    kh.getHocPhi(),
                    helPer.DateHP.toString(kh.getNgayKG()),
                    kh.getMaNV(),
                    helPer.DateHP.toString(kh.getNgayTao())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            helPer.DialogHP.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        CLASS.khoaHoc model = getModel();
        model.setNgayTao(new Date());
        try {
            dao.insert(model);
            this.load();
            this.clear();
            helPer.DialogHP.alert(this, "Thêm mới thành công!");
        } catch (HeadlessException e) {
            helPer.DialogHP.alert(this, "Thêm mới thất bại!");
        }
    }

    void update() {
        CLASS.khoaHoc model = getModel();
        try {
            dao.update(model);
            this.load();
            helPer.DialogHP.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            helPer.DialogHP.alert(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        if (helPer.DialogHP.confirm(this, "Bạn thực sự muốn xóa khóa học này?")) {
            Integer makh = Integer.valueOf(jComboBox1.getToolTipText());
            try {
                dao.delete(makh);
                this.load();
                this.clear();
                helPer.DialogHP.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                helPer.DialogHP.alert(this, "Xóa thất bại!");
            }
        }
    }

    void clear() {
        CLASS.khoaHoc model = new khoaHoc();
        CLASS.chuyenDe chuyenDe = (chuyenDe) jComboBox1.getSelectedItem();
        model.setMaCD(chuyenDe.getMaCD());
        model.setMaNV(helPer.ShareHP.USER.getMaNV());
        model.setNgayKG(helPer.DateHP.add(30));
        model.setNgayTao(helPer.DateHP.now());

        this.setModel(model);
    }

    void edit() {
        try {
            Integer makh = (Integer) jTable1.getValueAt(this.index, 0);
            CLASS.khoaHoc model = dao.findById(makh);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            helPer.DialogHP.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
void check(){
    if(jTextField3.getText().isBlank()){
        helPer.DialogHP.alert(this, "Thời Lượng bị trống");
    }else if(jTextField2.getText().isBlank()){
    
        helPer.DialogHP.alert(this, "Học Phí bị trống");
    }else if(!jTextField3.getText().isBlank()||!jTextField2.getText().isBlank()){
            String hp = "java.lang.NumberFormatException: For input string: ";
            String tl2 = "java.lang.NumberFormatException: For input string: ";
            String tl = "";
            try {
                    int thoiluong = Integer.parseInt(jTextField3.getText());
                    int hocphi = Integer.parseInt(jTextField2.getText());

                    if (thoiluong <= 0) {
                        helPer.DialogHP.alert(this, "Thời lượng là số dương và phải lớn hơn 0");
                    } else if (hocphi <= 0) {
                        helPer.DialogHP.alert(this, "Học phí là số dương và phải lớn hơn 0");
                    } else {
                        flag = true;
                    }
                
            } catch (Exception e) {
                System.out.println(e);
                tl += e.toString();
                hp += "\"" + (jTextField2.getText()).toString() + "\"";
                tl2 += "\"" + (jTextField3.getText()).toString() + "\"";
                if (tl.equals(hp)) {
                    helPer.DialogHP.alert(this, "Học phí phải truyền vào kiểu số!");
                } else if (tl.equals(tl2)) {
                    helPer.DialogHP.alert(this, "Thời lượng phải truyền vào kiểu số!");
                }
            }
    }else if(jTextField1.getText().isBlank()){
        helPer.DialogHP.alert(this, "Ngày khai giảng bị trống");
    }else if(jTextField5.getText().isBlank()){
        helPer.DialogHP.alert(this, "Ngày tạo đang để trống");
    }else{
        flag=true;
    }
}
    void setModel(CLASS.khoaHoc model) {
        jComboBox1.setToolTipText(String.valueOf(model.getMaKH()));
        jComboBox1.setSelectedItem(cddao.findById(model.getMaCD()));
        jTextField1.setText(helPer.DateHP.toString(model.getNgayKG()));
        jTextField2.setText(String.valueOf(model.getHocPhi()));
        jTextField3.setText(String.valueOf(model.getThoiLuong()));
        jTextField5.setText(helPer.DateHP.toString(model.getNgayTao()));
        jTextField6.setText(model.getGhiChu());
    }

    CLASS.khoaHoc getModel() {
        CLASS.khoaHoc model = new khoaHoc();
        CLASS.chuyenDe chuyenDe = (chuyenDe) jComboBox1.getSelectedItem();
        model.setMaCD(chuyenDe.getMaCD());
        model.setNgayKG(helPer.DateHP.toDate(jTextField1.getText()));
        model.setHocPhi(Double.valueOf(jTextField2.getText()));
        model.setThoiLuong(Integer.valueOf(jTextField3.getText()));
        model.setGhiChu(jTextField6.getText());
        model.setMaNV(helPer.ShareHP.USER.getMaNV());
        model.setNgayTao(helPer.DateHP.toDate(jTextField5.getText()));
        model.setMaKH(Integer.valueOf(jComboBox1.getToolTipText()));
        return model;
    }

    void setStatus(boolean insertable) {
        jButton1.setEnabled(insertable);
        jButton2.setEnabled(!insertable);
        jButton3.setEnabled(!insertable);

        boolean first = this.index > 0;
        boolean last = this.index < jTable1.getRowCount() - 1;
        jButton5.setEnabled(!insertable && first);
        jButton6.setEnabled(!insertable && first);
        jButton7.setEnabled(!insertable && last);
        jButton8.setEnabled(!insertable && last);

        jButton9.setVisible(!insertable);
    }

    void selectComboBox() {
        CLASS.chuyenDe chuyenDe = (chuyenDe) jComboBox1.getSelectedItem();
        jTextField3.setText(String.valueOf(chuyenDe.getThoiLuong()));
        jTextField2.setText(String.valueOf(chuyenDe.getHocPhi()));
    }

    void openHocVien() {
        Integer id = Integer.valueOf(jComboBox1.getToolTipText());
        new hocvienKhoaHoc(id).setVisible(true);
    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) jComboBox1.getModel();
        model.removeAllElements();
        try {
            List<CLASS.chuyenDe> list = cddao.select();
            for (CLASS.chuyenDe cd : list) {
                model.addElement(cd);
            }
        } catch (Exception e) {
            helPer.DialogHP.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("QUẢN LÝ KHÓA HỌC");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ KH", "CHUYÊN ĐỀ", "THỜI LƯỢNG", "HỌC PHÍ", "KHAI GIẢNG", "TẠO BỞI", "NGÀY TẠO"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh Sách", jPanel1);

        jLabel2.setText("Chuyên Đề");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Ngày Khai Giảng");

        jLabel4.setText("Học Phí");

        jLabel5.setText("Thời Lượng(Giờ)");

        jLabel6.setText("Người Tạo");

        jLabel7.setText("Ngày Tạo");

        jLabel8.setText("Ghi chú");

        jButton8.setText(">|");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton7.setText(">>");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton6.setText("<<");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setText("|<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setText("Mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton9.setText("Học Viên");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7)
                                .addGap(7, 7, 7)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField6)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton9)))
                                .addGap(15, 15, 15)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jTextField3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cập Nhập", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           check();
        if (flag == true) {
            try {
                String nkg = jTextField1.getText();
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(nkg);
                if (date.before(new Date())) {
                    helPer.DialogHP.alert(this, "Ngày khai giảng phải sau ngày hiện tại!");
                    return;
                } else {
                    insert();
                }
            } catch (Exception e) {
                helPer.DialogHP.alert(this, "Sai định dạng ngày!");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
           check();
        if (flag == true) {
            try {
                String nkg = jTextField1.getText();
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(nkg);
                if (date.before(new Date())) {
                    helPer.DialogHP.alert(this, "Ngày khai giảng phải sau ngày hiện tại!");
                    return;
                } else {
                    update();
                }
            } catch (Exception e) {
                helPer.DialogHP.alert(this, "Sai định dạng ngày!");
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (helPer.ShareHP.USER.getVaiTro() == true) {
            delete();
        } else {
            helPer.DialogHP.alert(this, "Bạn không có quyền xóa !!!");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       clear();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(a==null){
            helPer.DialogHP.alert(this, "Chưa Chọn học viên");
        }else {
            new hocvienKhoaHoc(a).setVisible(true);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 1) {
            this.index = jTable1.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                String name = (String) jTable1.getValueAt(this.index, 5);
                Integer b = (Integer) jTable1.getValueAt(this.index, 0);
                this.edit();
                //lay ten chuyen dde, hoc phi, thoi luong -> cajp nhat
                a = b;
                jTextField1.setText(name);
                jTabbedPane1.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.index=0;
        this.edit();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.index--;
        this.edit();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.index++;
        this.edit();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.index = jTable1.getRowCount() - 1;
        this.edit();
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(quanLyKhoaHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(quanLyKhoaHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(quanLyKhoaHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(quanLyKhoaHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new quanLyKhoaHoc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
