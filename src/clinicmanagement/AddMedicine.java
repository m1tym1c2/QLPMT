package clinicmanagement;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 *
 * @author ngoctienTNT
 */
public class AddMedicine extends javax.swing.JDialog {

    public static AddMedicine it;
    public static double Tyle = 0;
    public static String mathuoc = "";
    public static String donvi = "";
    public static String soluong = "";

    static void setData(double tyle) {
        Tyle = tyle/100;
    }
    
    public AddMedicine(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        getContentPane().setBackground(Color.white);
        it = this;
        try
        {
            LoadData();           
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Chưa có thuốc! ", "Lỗi ", ERROR_MESSAGE);
        }
    }
    public void LoadData()throws SQLException{
        
        jComboBox1.removeAllItems();
        
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();        
        
        ResultSet rs = stm.executeQuery("SELECT DISTINCT  TenThuoc  FROM THUOC ");
        rs = stm.executeQuery("SELECT DISTINCT TenThuoc FROM THUOC " );
        while(rs.next())    jComboBox1.addItem(rs.getString("TenThuoc"));        
        
        jComboBox1.setSelectedIndex(0);
        String tenthuoc = jComboBox1.getSelectedItem().toString();
 
        rs = stm.executeQuery("SELECT MaThuoc, TenDonViTinh, SoLuongTon FROM THUOC WHERE  TenThuoc = N'" + tenthuoc + "' ");
        
        rs.next();
        mathuoc = rs.getString("MaThuoc");
        donvi = rs.getString("TenDonViTinh");
        soluong = String.valueOf(rs.getString("SoLuongTon"));
        jLabel6.setText(donvi);
        jLabel7.setText(soluong);
        
        rs.close(); 
        stm.close();
        conn.close();
    }
    
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public int LUUDATA()throws SQLException{
        
        if(jTextField2.getText()==null || jTextField1.getText()==null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đủ thông tin! ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();        
        
        if( !isNumeric(jTextField1.getText()) || !isNumeric(jTextField2.getText())){
            JOptionPane.showMessageDialog(this, "Số lượng và giá tiền cần phải là số!", "Lỗi dữ liệu!", ERROR_MESSAGE);
            return 0;
        }
        
        java.time.LocalDate Now = java.time.LocalDate.now();
        java.time.LocalTime time = java.time.LocalTime.now();
        String thang = String.valueOf(Now.getMonthValue()) ;
        String ngay = String.valueOf(Now.getDayOfMonth()) ;
        String giay = String.valueOf(time.getMinute()) ;
        if(thang.length()==1) thang = "0"+ thang;
        if(ngay.length()==1) ngay = "0"+ ngay;
        if(giay.length()==1) giay = "0"+ giay;
        String maphieunhapthuoc = String.valueOf(Now.getYear()) + thang  + ngay + 
                String.valueOf(time.getHour())  + String.valueOf(time.getMinute()) + giay ;  
        
        
        double soluongthem = Double.parseDouble(jTextField1.getText());
        double gianhap = Double.parseDouble(jTextField2.getText());
        
        String strDate = java.time.LocalDate.now().getDayOfMonth() + "-"  + java.time.LocalDate.now().getMonthValue() + "-"  + java.time.LocalDate.now().getYear();
        stm.executeUpdate("insert into PHIEUNHAPTHUOC VALUES (N'"+ maphieunhapthuoc + "',"+ gianhap*soluongthem 
                + ",'"+java.time.LocalDate.now()+"') "); 
        stm.executeUpdate("insert into CT_PHIEUNHAPTHUOC VALUES (N'"+ maphieunhapthuoc +"','"
                + mathuoc + "',"+ soluongthem+","+gianhap+","+ gianhap*Tyle + ") ");
        
         
        
        
        double soluongton = Double.parseDouble(soluong);
        stm.executeUpdate("UPDATE THUOC set SoLuongTon = "+ (soluongton + soluongthem) +" WHERE MaThuoc = '"+mathuoc+"'  ");    
        
        stm.close();
        conn.close();
        return 1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        XACNHAN = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        QUAYLAI = new javax.swing.JButton();

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/Dấu X.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thêm thuốc");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(1, 84, 43));
        jLabel2.setText("Tên thuốc:");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.setPreferredSize(new java.awt.Dimension(100, 18));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(1, 84, 43));
        jLabel1.setText("Đơn vị tính:");

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField2.setPreferredSize(new java.awt.Dimension(100, 18));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(1, 84, 43));
        jLabel3.setText("Số lượng tồn:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(1, 84, 43));
        jLabel4.setText("Số lượng thêm:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(1, 84, 43));
        jLabel5.setText("Đơn giá nhập:");

        XACNHAN.setBackground(new java.awt.Color(255, 204, 204));
        XACNHAN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        XACNHAN.setForeground(new java.awt.Color(0, 99, 28));
        XACNHAN.setText("Xác nhận");
        XACNHAN.setToolTipText("");
        XACNHAN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        XACNHAN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                XACNHANMouseClicked(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("jLabel6");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("jLabel7");

        QUAYLAI.setBackground(new java.awt.Color(255, 255, 255));
        QUAYLAI.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        QUAYLAI.setForeground(new java.awt.Color(255, 255, 255));
        QUAYLAI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/Dấu X.png"))); // NOI18N
        QUAYLAI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                QUAYLAIMouseClicked(evt);
            }
        });
        QUAYLAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QUAYLAIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(XACNHAN, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(QUAYLAI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(QUAYLAI, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(XACNHAN, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void XACNHANMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XACNHANMouseClicked
       try
        {
            if(LUUDATA()==1)
            {
                JOptionPane.showMessageDialog(this, "Bạn đã thêm thuốc thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                MedicineUsageManagement frame = new MedicineUsageManagement();
                this.dispose();
                frame.setVisible(true);
            }            
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }        
        
    }//GEN-LAST:event_XACNHANMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MedicineUsageManagement().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void QUAYLAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QUAYLAIActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MedicineUsageManagement().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_QUAYLAIActionPerformed

    private void QUAYLAIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_QUAYLAIMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MedicineUsageManagement().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_QUAYLAIMouseClicked

    public void ChangeItem()throws SQLException{
        
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();        
        
        String tenthuoc = jComboBox1.getSelectedItem().toString();
 
        ResultSet rs = stm.executeQuery("SELECT MaThuoc, TenDonViTinh, SoLuongTon FROM THUOC WHERE  TenThuoc = N'" + tenthuoc + "' ");
        
        rs.next();
        mathuoc = rs.getString("MaThuoc");
        donvi = rs.getString("TenDonViTinh");
        soluong = String.valueOf(rs.getString("SoLuongTon"));
        jLabel6.setText(donvi);
        jLabel7.setText(soluong);
        
        rs.close(); 
        stm.close();
        conn.close();
    }
    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        try
        {
            ChangeItem();           
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged


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
            java.util.logging.Logger.getLogger(AddMedicine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddMedicine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddMedicine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddMedicine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            AddMedicine dialog = new AddMedicine(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton QUAYLAI;
    private javax.swing.JButton XACNHAN;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
