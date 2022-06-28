package clinicmanagement;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.joda.time.DateTime;

/**
 *
 * @author Dell
 */
public class ChiTietHoaDon extends javax.swing.JDialog {

    private Connection connection = null;
    static String maPhieuKhamBenh = "";
    private static String CMND = "";
    private static String MaNhanVien = "";
    private static int TongTien = 0;

    public String getMaPhieuKhamBenh() {
        return maPhieuKhamBenh;
    }

    public void setMaPhieuKhamBenh(String maPhieuKhamBenh) {
        this.maPhieuKhamBenh = maPhieuKhamBenh;
    }

    @Override
    public void setVisible(boolean b) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            connection = databaseConnection.getConnection(this);
            Statement statement = connection.createStatement();
            //PHIEUKHAMBENH, CT_PHIEUKHAMBENH, LOAIBENH, BENHNHAN, THUOC, DONVITINH, CACHDUNG
            ResultSet rs = statement.executeQuery("SELECT * FROM PHIEUKHAMBENH, BENHNHAN, LOAIBENH "
                    + "WHERE PHIEUKHAMBENH.MaBenhNhan = BENHNHAN.MaBenhNhan AND LOAIBENH.MaLoaiBenh = PHIEUKHAMBENH.MaLoaiBenh "
                    + "AND PHIEUKHAMBENH.MaPhieuKhamBenh = '" + maPhieuKhamBenh + "'");

            rs.next();
            SimpleDateFormat simp = new SimpleDateFormat("dd/MM/yyyy");
            name.setText(rs.getString("TenBenhNhan"));
            date.setText(simp.format(rs.getDate("NgayKham")));
            TrieuChung.setText(rs.getString("TrieuChung"));
            jLabel3.setText(rs.getString("TenLoaiBenh"));
            tienKham.setText(String.format("%,d",rs.getInt("TienKham")));
            int money = rs.getInt("TienKham");

            rs = statement.executeQuery("SELECT * FROM CT_PHIEUKHAMBENH, THUOC, DONVITINH "
                    + "WHERE CT_PHIEUKHAMBENH.MaThuoc = THUOC.MaThuoc AND THUOC.TenDonViTinh = DONVITINH.TenDonViTinh "
                    + "AND CT_PHIEUKHAMBENH.MaPhieuKhamBenh = '" + maPhieuKhamBenh + "'");
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            int i = 0;
            int sum = 0;

            while (rs.next()) {
                i++;
                String data[] = {Integer.toString(i), rs.getString("TenThuoc"), rs.getString("TenDonViTinh"),
                    rs.getString("SoLuongDung"),String.format("%,d",rs.getInt("DonGiaThuoc")),
                    String.format("%,d",rs.getInt("DonGiaThuoc") * rs.getInt("SoLuongDung"))};
                sum += rs.getInt("DonGiaThuoc") * rs.getInt("SoLuongDung");
                DefaultTableModel tbModel = (DefaultTableModel) jTable1.getModel();
                tbModel.addRow(data);
            }
            TongTien = sum + money;
            tongTien.setText(String.format("%,d",(sum + money)) + " VND");
            rs = statement.executeQuery("SELECT NHANVIEN.MaNhanVien, TenNhanVien FROM NHANVIEN, PHIEUKHAMBENH WHERE PHIEUKHAMBENH.MaNhanVien = NHANVIEN.MaNhanVien AND PHIEUKHAMBENH.MaPhieuKhamBenh = '"+maPhieuKhamBenh+"'");
            while (rs.next())
            {
                MaNhanVien = rs.getString(1);
                NguoiLap.setText(rs.getString(2));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietBaoCaoThang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            super.setVisible(b);
        }
    }
    
    public BufferedImage createImage(JPanel panel) {

        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paint(g);
        return bi;
    }

    public ChiTietHoaDon(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        getContentPane().setBackground(Color.white);
    }

    public ChiTietHoaDon(java.awt.Frame parent, boolean modal, String CMND) {
        super(parent, modal);
        initComponents();
        this.CMND = CMND;
        getContentPane().setBackground(Color.white);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        Tentrang1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TrieuChung = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tienKham = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        NguoiLap = new javax.swing.JLabel();
        tongTien = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 780));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 99, 28));
        jButton1.setText("In hóa đơn");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 640, 175, 36));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Xóa hóa đơn");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 640, 175, 36));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Tentrang1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentrang1.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang1.setText("PHIẾU KHÁM BỆNH\n");
        jPanel1.add(Tentrang1, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 19, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 84, 42));
        jLabel1.setText("Họ tên:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 81, 122, -1));

        name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        name.setText("Nguyễn Đình Đức Thịnh");
        jPanel1.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 81, -1, -1));

        date.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        date.setText("25/4/2022");
        jPanel1.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 121, 192, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 84, 42));
        jLabel4.setText("Ngày khám:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 121, 183, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 84, 42));
        jLabel2.setText("Triệu chứng:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 162, 122, 21));

        TrieuChung.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TrieuChung.setText("Nguyễn Đình Đức Thịnh");
        jPanel1.add(TrieuChung, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 161, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("jLabel3");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 202, 192, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 84, 42));
        jLabel5.setText("Chuẩn đoán loại bệnh:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 201, -1, 28));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 84, 42));
        jLabel6.setText("Tiền khám: ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 247, -1, -1));

        tienKham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tienKham.setText("30,000");
        jPanel1.add(tienKham, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 247, 192, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 84, 42));
        jLabel8.setText("Danh mục thuốc dùng:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 287, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên thuốc", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 586, 141));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 84, 42));
        jLabel7.setText("Người lập hóa đơn:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 538, -1, 30));

        NguoiLap.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        NguoiLap.setText("Lê Phi Long");
        jPanel1.add(NguoiLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 542, 380, -1));

        tongTien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tongTien.setText("206,000");
        jPanel1.add(tongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 592, 379, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 84, 42));
        jLabel9.setText("Tổng tiền:\n");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 588, -1, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 0, 680, 630));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            connection = databaseConnection.getConnection(this);
            try ( PreparedStatement ps = connection.prepareStatement("DELETE FROM CT_PHIEUKHAMBENH WHERE MaPhieuKhamBenh = ?")) {
                ps.setString(1, maPhieuKhamBenh);
                ps.executeUpdate();
            }

            try ( PreparedStatement ps = connection.prepareStatement("DELETE FROM PHIEUKHAMBENH WHERE MaPhieuKhamBenh = ?")) {
                ps.setString(1, maPhieuKhamBenh);
                ps.executeUpdate();
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        java.awt.EventQueue.invokeLater(() -> {
            new PhieuKhamBenh(CMND).setVisible(true);
        });
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Desktop desktop = Desktop.getDesktop();  
            DateTime time = DateTime.now();
            File outputfile = new File("D:\\HD_"+time.toString("yyyy_MM_dd_HH_mm_ss")+".png");
            ImageIO.write(createImage(jPanel1), "png", outputfile);
            JOptionPane.showMessageDialog(this, "Đã lưu ở "+outputfile.getAbsolutePath());
            if (outputfile.exists()) //checks file exists or not  
            {
                desktop.open(outputfile);              //opens the specified file  
            }
        } catch (Exception ex) {
            Logger.getLogger(BillList.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            DatabaseConnection dtc = new DatabaseConnection();
            Connection conn = dtc.getConnection(this);
            Statement statement = conn.createStatement();
            boolean flag = true;
            int num = 1;
            String MaHoaDon = "NULL";
            ResultSet rs;
            while (flag) {
                if (num < 10) {
                    MaHoaDon = "HD00" + num;
                } else if (num < 100) {
                    MaHoaDon = "HD0" + num;
                } else {
                    MaHoaDon = "HD" + num;
                }
                rs = statement.executeQuery("select mahoadon from hoadon where exists (select mahoadon from hoadon where mahoadon ='" + MaHoaDon +"')");
                if (rs.next() == false)
                    flag=false;
                else
                    num++;
            }
            statement.executeUpdate("INSERT INTO HOADON VALUES('"+MaHoaDon+"','"+maPhieuKhamBenh+"',"+String.valueOf(TongTien)+",'"+MaNhanVien+"')");
            JOptionPane.showMessageDialog(this, "Hóa đơn đã được in");
            java.awt.EventQueue.invokeLater(() -> {
            new DanhSachKhamBenh(CMND).setVisible(true);
        });
            this.dispose();
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ChiTietHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChiTietHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChiTietHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChiTietHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            ChiTietHoaDon dialog = new ChiTietHoaDon(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel NguoiLap;
    private javax.swing.JLabel Tentrang1;
    private javax.swing.JLabel TrieuChung;
    private javax.swing.JLabel date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel name;
    private javax.swing.JLabel tienKham;
    private javax.swing.JLabel tongTien;
    // End of variables declaration//GEN-END:variables
}
