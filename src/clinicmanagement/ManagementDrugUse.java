package clinicmanagement;

import static clinicmanagement.AddNewMedicine.scaleImage;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ngoctienTNT
 */
public class ManagementDrugUse extends javax.swing.JFrame {

    public static ManagementDrugUse it;
    public static String mathuoc = "";
    static void SetData(String Mathuoc) {
        mathuoc = Mathuoc;
    }
    public ManagementDrugUse() throws Exception {
         initComponents();    
        getContentPane().setBackground(Color.white);
        it = this;
        try
        {
            LoadData();           
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }
    public void LoadData()throws SQLException, MalformedURLException, IOException, Exception{
        
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();        
        
        ResultSet rs = stm.executeQuery("SELECT TenThuoc ,SoLuongTon, TenDonViTinh , LoaiThuoc, TenCachDung, FileAnhThuoc  "
                + "                      FROM THUOC  , CACHDUNG "
                + "                      WHERE THUOC.MaCachDung  = CACHDUNG.MaCachDung "
                + "                      AND MaThuoc = N'"+ mathuoc +"';");
        
        rs.next();
        jTextField1.setText(rs.getString("TenThuoc"));
        jTextField3.setText(rs.getString("LoaiThuoc"));
        jTextArea1.setText(rs.getString("TenCachDung"));
        jComboBox1.addItem(rs.getString("TenDonViTinh"));
        jLabel7.setText(String.valueOf(rs.getInt("SoLuongTon")));        
        // load ảnh                
        //ImageIcon ii = new ImageIcon(scaleImage(310, 320, ImageIO.read(new File(rs.getString("FileAnhThuoc")))));//get the image from file chooser and scale it to match JLabel size
        //Anh.setIcon(ii);        
        
        rs = stm.executeQuery("SELECT TenDonViTinh  FROM DONVITINH");
        while(rs.next()) jComboBox1.addItem(rs.getString("TenDonViTinh"));
        
        rs = stm.executeQuery("SELECT NgayNhap ,SoLuongNhap ,DonGiaNhap ,DonGiaBan "
                + "                      FROM  CT_PHIEUNHAPTHUOC , PHIEUNHAPTHUOC "
                + "                      WHERE CT_PHIEUNHAPTHUOC.MaPhieuNhapThuoc = PHIEUNHAPTHUOC.MaPhieuNhapThuoc "
                + "                      AND CT_PHIEUNHAPTHUOC.MaThuoc = N'"+ mathuoc +"';");
        
        //load bảng
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while (rs.next())
        {
            Vector row = new Vector();          
            row.add(model.getRowCount()+1);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");        
            String strDate = formatter.format(rs.getDate("NgayNhap"));
            row.add(strDate);
            row.add(String.valueOf(rs.getInt("SoLuongNhap")));
            row.add(String.valueOf(rs.getInt("DonGiaNhap")));
            row.add(String.valueOf(rs.getInt("DonGiaBan")));
            model.getRowCount();
            model.addRow(row);            
        }
        
        rs.close(); 
        stm.close();
        conn.close();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        avatar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        buttonOption = new customview.MyButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        XOAthuoc = new customview.MyButton();
        SUA = new customview.MyButton();
        buttonBack = new customview.MyButton();
        Anh = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(208, 242, 224));

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/assets/icon.png"));
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        icon.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        icon.setIcon(imageIcon);
        icon.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel.setText("QUẢN LÝ SỬ DỤNG THUỐC");

        avatar.setText("Avatar");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Trần Ngọc Tiến");

        ImageIcon imageIconArrow = new ImageIcon(getClass().getResource("/assets/down-arrow.png"));
        Image imageArrow = imageIconArrow.getImage();
        Image newimgArrow = imageArrow.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        imageIconArrow = new ImageIcon(newimgArrow);
        buttonOption.setIcon(imageIconArrow);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonOption, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(buttonOption, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Tên thuốc:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, 130, -1));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 280, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Đơn vị tính:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 130, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Số lượng tồn:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 130, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 280, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Loại thuốc:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 190, 95, -1));

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, 280, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Cách dùng:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 95, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(jTextArea1);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 240, 280, 70));

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 280, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("THÔNG TIN THUỐC");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, -1));

        XOAthuoc.setText("Xóa thuốc");
        XOAthuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        XOAthuoc.setRadius(15);
        XOAthuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                XOAthuocMouseClicked(evt);
            }
        });
        jPanel3.add(XOAthuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 410, 110, -1));

        SUA.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SUA.setLabel("Cập nhật thông tin");
        SUA.setRadius(15);
        SUA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SUAMouseClicked(evt);
            }
        });
        jPanel3.add(SUA, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 410, -1, -1));

        buttonBack.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonBack.setLabel("Quay lại");
        buttonBack.setRadius(15);
        buttonBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonBackMouseClicked(evt);
            }
        });
        jPanel3.add(buttonBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 410, 120, -1));

        Anh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/demo.jpg"))); // NOI18N
        Anh.setText("jLabel14");
        jPanel3.add(Anh, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 310, 320));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Ngày nhập", "SL", "Giá nhập", "Giá bán"
            }
        ));
        jScrollPane2.setViewportView(table);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, -1, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public void DELETE()throws SQLException{        
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();       
        
        stm.executeUpdate("DELETE FROM THUOC WHERE MaThuoc = '"+ mathuoc +"';");
        stm.close();
        conn.close();
    }
    private void XOAthuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XOAthuocMouseClicked
        int reply = JOptionPane.showConfirmDialog( null,"Bạn có chắc xóa loại thuốc này?" , "!!!",JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try
            {
                DELETE();
                JOptionPane.showMessageDialog(this, "Xóa thành công!" );
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new MedicineUsageManagement().setVisible(true);
                    }
                });
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }
        } 
    }//GEN-LAST:event_XOAthuocMouseClicked
    public void SUA()throws SQLException{        
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();       
        
        stm.executeUpdate("UPDATE THUOC set TenThuoc = N'"+jTextField1.getText()+"', "
                + "TenDonViTinh = N'"+ jComboBox1.getSelectedItem().toString() 
                + "', LoaiThuoc= N'" + jTextField3.getText()
                + "' WHERE MaThuoc = N' "+ mathuoc +"';");
        
        ResultSet rs = stm.executeQuery("SELECT MaCachDung  FROM THUOC");
        rs.next();
        String madung = rs.getString("MaCachDung");
        stm.executeUpdate("UPDATE CACHDUNG set TenCachDung = N'" + jTextArea1.getText()+ "' WHERE MaCachDung = '"+ madung + "'");
        
        stm.close();
        conn.close();
    }
    private void SUAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SUAMouseClicked
        try
            {
                SUA();
                JOptionPane.showMessageDialog(this, "Sửa thành công!" );
                java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MedicineUsageManagement().setVisible(true);
                    }
                });
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }

    }//GEN-LAST:event_SUAMouseClicked

    private void buttonBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonBackMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MedicineUsageManagement().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_buttonBackMouseClicked
    
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagementDrugUse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new ManagementDrugUse().setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(ManagementDrugUse.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anh;
    private customview.MyButton SUA;
    private customview.MyButton XOAthuoc;
    private javax.swing.JLabel avatar;
    private customview.MyButton buttonBack;
    private customview.MyButton buttonOption;
    private javax.swing.JLabel icon;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
