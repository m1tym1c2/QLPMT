/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicmanagement;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author admin
 */
public class KeThuoc extends javax.swing.JFrame {

    /**
     * Creates new form KeThuoc
     */
    public static String MaPhieuKhamBenh;
    public static String MaThuoc;
    private static String CMND = "";
    public KeThuoc() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        DocDuLieu();
        PhieuKhamBenh.MoLanDau = false;
    }
    
    public KeThuoc(String CMND) throws SQLException {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setLocationRelativeTo(null);
        this.CMND = CMND;
        DocDuLieu();
    }
    private void DocDuLieu() throws SQLException
    {
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT TenThuoc FROM THUOC");
        while (rs.next())
        {
            TenThuoc.addItem(rs.getString(1));
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        SoLuong = new javax.swing.JTextField();
        TenThuoc = new javax.swing.JComboBox<>();
        Tentrang2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        DonViTinh = new javax.swing.JLabel();
        CachDung = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 84, 42));
        jLabel2.setText("Tên thuốc");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 84, 42));
        jLabel3.setText("Đơn vị tính");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 84, 42));
        jLabel4.setText("Số lượng");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 84, 42));
        jLabel5.setText("Cách dùng");

        SoLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        TenThuoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TenThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TenThuocActionPerformed(evt);
            }
        });

        Tentrang2.setBackground(new java.awt.Color(255, 255, 255));
        Tentrang2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentrang2.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang2.setText("KÊ THUỐC BỆNH NHÂN");

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Thêm");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        DonViTinh.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        CachDung.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TenThuoc, 0, 303, Short.MAX_VALUE)
                                    .addComponent(DonViTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CachDung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(Tentrang2)
                .addGap(81, 81, 81))
            .addGroup(layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(Tentrang2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TenThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(DonViTinh))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(CachDung))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TenThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TenThuocActionPerformed
         try
         {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT MaThuoc, TenDonViTinh, TenCachDung FROM THUOC, CACHDUNG WHERE THUOC.MaCachDung = CACHDUNG.MaCachDung AND TenThuoc = N'"+TenThuoc.getSelectedItem().toString()+"'");
            while (rs.next())
            {
                MaThuoc = rs.getString(1);
                DonViTinh.setText(rs.getString(2));
                CachDung.setText(rs.getString(3));
            }
         }
         catch (Exception e)
         {
             
         }
    }//GEN-LAST:event_TenThuocActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT SoLuongTon FROM THUOC WHERE MaThuoc = '" + MaThuoc + "'");
            boolean flag = false;
            while (rs.next()) {
                if (rs.getInt(1) - Integer.parseInt(SoLuong.getText()) >= 0) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
            if (flag) {
                rs = stm.executeQuery("SELECT DonGiaBan FROM CT_PHIEUNHAPTHUOC WHERE MaThuoc = '" + MaThuoc + "'");
                String DonGiaBan = "NULL";
                while (rs.next()) {
                    DonGiaBan = String.valueOf(rs.getInt(1));
                }
                stm.executeUpdate("INSERT INTO CT_PHIEUKHAMBENH VALUES ('" + MaPhieuKhamBenh + "','" + MaThuoc + "'," + SoLuong.getText() + "," + DonGiaBan + ")");
                stm.executeUpdate("UPDATE THUOC SET SoLuongTon = SoLuongTon - " + SoLuong.getText() + "WHERE MaThuoc = '"+MaThuoc+"'");
                PhieuKhamBenh frame = new PhieuKhamBenh();
                frame.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Lượng thuốc trong kho hiện không đủ", "Không đủ thuốc", 1);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(KeThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KeThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KeThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KeThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try
                {
                    new KeThuoc().setVisible(true);
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null, e.toString());
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CachDung;
    private javax.swing.JLabel DonViTinh;
    private javax.swing.JTextField SoLuong;
    private javax.swing.JComboBox<String> TenThuoc;
    private javax.swing.JLabel Tentrang2;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
