/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicmanagement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author admin
 */
public class DanhSachChucVu extends javax.swing.JFrame {

    private String MaChucVuCu = "";


    public DanhSachChucVu() {
        initComponents();
        this.setLocationRelativeTo(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        JTableHeader header = Table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(Table));
        getContentPane().setBackground(Color.white);
        HienThi();
        Table.setAutoCreateRowSorter(true);

    }

    private void ConvertMaChucNang() {
        String MaChucNang = MaChucVu.getText();
        Thuoc.setSelected(false);
        DoanhThu.setSelected(false);
        NhanVien.setSelected(false);
        KhamBenh.setSelected(false);
        if ("000".equals(MaChucNang) || "001".equals(MaChucNang) || "003".equals(MaChucNang) || "005".equals(MaChucNang) || "007".equals(MaChucNang)
                || "009".equals(MaChucNang) || "011".equals(MaChucNang) || "013".equals(MaChucNang) || "015".equals(MaChucNang)) {
            Thuoc.setSelected(true);
        }
        if ("000".equals(MaChucNang) || "002".equals(MaChucNang) || "003".equals(MaChucNang) || "006".equals(MaChucNang) || "007".equals(MaChucNang)
                || "010".equals(MaChucNang) || "011".equals(MaChucNang) || "014".equals(MaChucNang) || "015".equals(MaChucNang)) {
            DoanhThu.setSelected(true);
        }
        if ("000".equals(MaChucNang) || "004".equals(MaChucNang) || "005".equals(MaChucNang) || "006".equals(MaChucNang) || "007".equals(MaChucNang)
                || "012".equals(MaChucNang) || "013".equals(MaChucNang) || "014".equals(MaChucNang) || "015".equals(MaChucNang)) {
            NhanVien.setSelected(true);
        }
        if ("000".equals(MaChucNang) || "008".equals(MaChucNang) || "009".equals(MaChucNang) || "010".equals(MaChucNang) || "011".equals(MaChucNang)
                || "012".equals(MaChucNang) || "013".equals(MaChucNang) || "014".equals(MaChucNang) || "015".equals(MaChucNang)) {
            KhamBenh.setSelected(true);
        }

    }

    private String ConvertChucNang() {
        if (Thuoc.isSelected() == true && DoanhThu.isSelected() == false && NhanVien.isSelected() == false && KhamBenh.isSelected() == false) {
            return "001";
        }
        if (Thuoc.isSelected() == false && DoanhThu.isSelected() == true && NhanVien.isSelected() == false && KhamBenh.isSelected() == false) {
            return "002";
        }
        if (Thuoc.isSelected() == true && DoanhThu.isSelected() == true && NhanVien.isSelected() == false && KhamBenh.isSelected() == false) {
            return "003";
        }
        if (Thuoc.isSelected() == false && DoanhThu.isSelected() == false && NhanVien.isSelected() == true && KhamBenh.isSelected() == false) {
            return "004";
        }
        if (Thuoc.isSelected() == true && DoanhThu.isSelected() == false && NhanVien.isSelected() == true && KhamBenh.isSelected() == false) {
            return "005";
        }
        if (Thuoc.isSelected() == false && DoanhThu.isSelected() == true && NhanVien.isSelected() == true && KhamBenh.isSelected() == false) {
            return "006";
        }
        if (Thuoc.isSelected() == true && DoanhThu.isSelected() == true && NhanVien.isSelected() == true && KhamBenh.isSelected() == false) {
            return "007";
        }
        if (Thuoc.isSelected() == false && DoanhThu.isSelected() == false && NhanVien.isSelected() == false && KhamBenh.isSelected() == true) {
            return "008";
        }
        if (Thuoc.isSelected() == true && DoanhThu.isSelected() == false && NhanVien.isSelected() == false && KhamBenh.isSelected() == true) {
            return "009";
        }
        if (Thuoc.isSelected() == false && DoanhThu.isSelected() == true && NhanVien.isSelected() == false && KhamBenh.isSelected() == true) {
            return "010";
        }
        if (Thuoc.isSelected() == true && DoanhThu.isSelected() == true && NhanVien.isSelected() == false && KhamBenh.isSelected() == true) {
            return "011";
        }
        if (Thuoc.isSelected() == false && DoanhThu.isSelected() == false && NhanVien.isSelected() == true && KhamBenh.isSelected() == true) {
            return "012";
        }
        if (Thuoc.isSelected() == true && DoanhThu.isSelected() == false && NhanVien.isSelected() == true && KhamBenh.isSelected() == true) {
            return "013";
        }
        if (Thuoc.isSelected() == false && DoanhThu.isSelected() == true && NhanVien.isSelected() == true && KhamBenh.isSelected() == true) {
            return "014";
        }
        if (Thuoc.isSelected() == true && DoanhThu.isSelected() == true && NhanVien.isSelected() == true && KhamBenh.isSelected() == true) {
            return "015";
        }
        return "000";
    }

    private void HienThi() {

        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM CHUCNANG");
            DefaultTableModel model = (DefaultTableModel) Table.getModel();
            while (model.getRowCount() != 0) {
                model.removeRow(0);
            }
            while (rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("MaChucNang"));
                vector.add(rs.getString("TenChucNang"));
                model.addRow(vector);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
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

        jButton5 = new javax.swing.JButton();
        Tentrang2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        Thuoc = new javax.swing.JRadioButton();
        DoanhThu = new javax.swing.JRadioButton();
        NhanVien = new javax.swing.JRadioButton();
        KhamBenh = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        TenChucVu = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        MaChucVu = new javax.swing.JTextField();

        jButton5.setBackground(new java.awt.Color(255, 204, 204));
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 99, 28));
        jButton5.setText("Thêm");
        jButton5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1141, 563));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Tentrang2.setBackground(new java.awt.Color(255, 255, 255));
        Tentrang2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Tentrang2.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang2.setText("DANH SÁCH CÁC CHỨC VỤ");
        getContentPane().add(Tentrang2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã chức vụ", "Tên chức vụ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setPreferredWidth(50);
            Table.getColumnModel().getColumn(1).setPreferredWidth(500);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 87, 748, 365));

        jButton3.setBackground(new java.awt.Color(255, 204, 204));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 99, 28));
        jButton3.setText("Thêm");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 480, 120, 40));

        jButton4.setBackground(new java.awt.Color(255, 204, 204));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 99, 28));
        jButton4.setText("Xóa");
        jButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 480, 120, 40));

        jButton6.setBackground(new java.awt.Color(255, 204, 204));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 99, 28));
        jButton6.setText("Sửa");
        jButton6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 480, 120, 40));

        Thuoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Thuoc.setText("Thuốc");
        Thuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThuocActionPerformed(evt);
            }
        });
        getContentPane().add(Thuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 120, -1, -1));

        DoanhThu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        DoanhThu.setText("Doanh Thu");
        DoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThuocActionPerformed(evt);
            }
        });
        getContentPane().add(DoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 120, -1, -1));

        NhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        NhanVien.setText("Nhân Viên");
        NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThuocActionPerformed(evt);
            }
        });
        getContentPane().add(NhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 160, -1, -1));

        KhamBenh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        KhamBenh.setText("Khám Bệnh");
        KhamBenh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThuocActionPerformed(evt);
            }
        });
        getContentPane().add(KhamBenh, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 160, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Tên Chức Vụ:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 240, -1, -1));

        TenChucVu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TenChucVu.setToolTipText("");
        getContentPane().add(TenChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 270, 260, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Mã Chức Vụ:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 320, -1, -1));

        MaChucVu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        MaChucVu.setText("000");
        MaChucVu.setToolTipText("");
        MaChucVu.setFocusable(false);
        getContentPane().add(MaChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 350, 260, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if ("000".equals(MaChucVu.getText())) {
            JOptionPane.showMessageDialog(this, "Xin hãy chọn chức năng", "Lỗi", ERROR_MESSAGE);
        } else if ("".equals(TenChucVu.getText())) {
            JOptionPane.showMessageDialog(this, "Xin hãy nhập tên chức năng", "Lỗi", ERROR_MESSAGE);
        } else {
            try {
                DatabaseConnection DTBC = new DatabaseConnection();
                Connection conn = DTBC.getConnection(this);
                Statement stm = conn.createStatement();
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                stm.execute("INSERT INTO CHUCNANG (MACHUCNANG, TENCHUCNANG) VALUES ('" + ConvertChucNang() + "',N'" + TenChucVu.getText() + "')");
                JOptionPane.showMessageDialog(this, "Đã thêm thành công");
                HienThi();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Đã có chức vụ này", "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if ("000".equals(MaChucVu.getText())) {
            JOptionPane.showMessageDialog(this, "Không thể xóa admin", "Lỗi", ERROR_MESSAGE);
        } else {
            try {
                DatabaseConnection DTBC = new DatabaseConnection();
                Connection conn = DTBC.getConnection(this);
                Statement stm = conn.createStatement();
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                stm.execute("DELETE from  CHUCNANG where TenChucNang = N'" + TenChucVu.getText() + "'");
                JOptionPane.showMessageDialog(this, "Đã xóa thành công");
                HienThi();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Chức vụ đang được sử dụng, không thể xóa", "Lỗi", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if ("000".equals(MaChucVu.getText())) {
            JOptionPane.showMessageDialog(this, "Không thể sửa admin", "Lỗi", ERROR_MESSAGE);
        } else {
            try {
                DatabaseConnection DTBC = new DatabaseConnection();
                Connection conn = DTBC.getConnection(this);
                Statement stm = conn.createStatement();
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                if (ConvertChucNang() == null ? MaChucVuCu != null : !ConvertChucNang().equals(MaChucVuCu)) {
                    try {
                        stm.execute("INSERT INTO CHUCNANG (MACHUCNANG, TENCHUCNANG) VALUES ('" + ConvertChucNang() + "',N'" + TenChucVu.getText() + "')");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Đã có chức vụ này", "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
                    }
                    stm.execute("UPDATE PHANQUYEN set MaChucNang = '" + ConvertChucNang() + "'"
                            + "  where MaChucNang = '" + MaChucVuCu + "'");
                    stm.execute("DELETE from  CHUCNANG where MaChucNang = '" + MaChucVuCu + "'");
                    JOptionPane.showMessageDialog(this, "Đã sửa thành công");
                }
                else {
                    stm.execute("UPDATE CHUCNANG set TenChucNang = N'" + TenChucVu.getText() + "'"
                            + "  where MaChucNang = '" + MaChucVuCu + "'");
                    JOptionPane.showMessageDialog(this, "Đã sửa thành công");
                }
                HienThi();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        JTable source = (JTable) evt.getSource();
        int row = source.rowAtPoint(evt.getPoint());
        MaChucVu.setText(source.getModel().getValueAt(row, 0) + "");
        TenChucVu.setText(source.getModel().getValueAt(row, 1) + "");
        MaChucVuCu = source.getModel().getValueAt(row, 0) + "";
        ConvertMaChucNang();
    }//GEN-LAST:event_TableMouseClicked

    private void ThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThuocActionPerformed
        MaChucVu.setText(ConvertChucNang());
    }//GEN-LAST:event_ThuocActionPerformed

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
            java.util.logging.Logger.getLogger(DanhSachChucVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DanhSachChucVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DanhSachChucVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DanhSachChucVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new DanhSachChucVu().setVisible(true);
                } catch (Exception e) {

                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton DoanhThu;
    private javax.swing.JRadioButton KhamBenh;
    private javax.swing.JTextField MaChucVu;
    private javax.swing.JRadioButton NhanVien;
    private javax.swing.JTable Table;
    private javax.swing.JTextField TenChucVu;
    private javax.swing.JLabel Tentrang2;
    private javax.swing.JRadioButton Thuoc;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
