package clinicmanagement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.swing.WindowConstants;

/**
 *
 * @author ngoct
 */
public class ClinicManagement extends javax.swing.JFrame {

    String encodedKey = "1v9BaydNlB2uI58JTO4HlQ==";
    static Cipher cipher;
    private Connection connection = null;

    public String DecryptPassword(String s) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        cipher = Cipher.getInstance("AES");
        return decrypt(s, originalKey);
    }

    public static String decrypt(String encryptedText, SecretKey secretKey)
            throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public ClinicManagement() {
        initComponents();
        MatKhau.setEchoChar((char) 0);
        MatKhau.setForeground(new Color(153, 153, 153));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        initBaoCaoThang();
        File myObj = new File("temp.log");
        ReadFile();
    }

    private void ReadFile() {
        try ( BufferedReader br = new BufferedReader(new FileReader("temp.log"))) {
            if ("true".equals(br.readLine())) {
                TaiKhoan.setText(br.readLine());
                MatKhau.setText(br.readLine());
                DangNhapMouseClicked(null);
                MatKhau.setEchoChar('\u25CF');
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClinicManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClinicManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int maxDay(int month, int year) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 30;
        }
    }

    private void initBaoCaoThang() {
        LocalDate localDate = LocalDate.now();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        try {
            boolean check = false;
            DatabaseConnection databaseConnection = new DatabaseConnection();
            connection = databaseConnection.getConnection(this);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM BAOCAOTHANG");
            while (rs.next()) {
                if (month == rs.getInt("Thang") && year == rs.getInt("Nam")) {
                    check = true;
                    break;
                }
            }

            if (!check) {
                PreparedStatement ps = connection.prepareStatement("SELECT GiaTriHoaDon, NgayKham FROM PHIEUKHAMBENH, HOADON "
                        + "WHERE HOADON.MaPhieuKhamBenh = PHIEUKHAMBENH.MaPhieuKhamBenh "
                        + "AND MONTH(NgayKham) = ? AND YEAR(NgayKham) = ?");
                ps.setInt(1, month);
                ps.setInt(2, year);
                rs = ps.executeQuery();
                int doanhThuThang = 0;
                while (rs.next()) {
                    doanhThuThang += rs.getInt("GiaTriHoaDon");
                }
                ps = connection.prepareStatement("INSERT INTO BAOCAOTHANG VALUES(?,?,?)");
                ps.setInt(1, month);
                ps.setInt(2, year);
                ps.setInt(3, doanhThuThang);
                ps.executeUpdate();

                for (int i = 0; i < maxDay(month, year); i++) {
                    ps = connection.prepareStatement("SELECT GiaTriHoaDon, NgayKham FROM PHIEUKHAMBENH, HOADON "
                            + "WHERE HOADON.MaPhieuKhamBenh = PHIEUKHAMBENH.MaPhieuKhamBenh "
                            + "AND MONTH(NgayKham) = ? AND YEAR(NgayKham) = ? AND DAY(NgayKham) = ?");
                    ps.setInt(1, month);
                    ps.setInt(2, year);
                    ps.setInt(3, i);
                    rs = ps.executeQuery();
                    int soBenhNhan = 0;
                    int doanhThuNgay = 0;
                    while (rs.next()) {
                        soBenhNhan++;
                        doanhThuNgay += rs.getInt("GiaTriHoaDon");
                    }
                    ps = connection.prepareStatement("INSERT INTO CT_BAOCAOTHANG VALUES(?,?,?,?,?,?)");
                    ps.setInt(1, i);
                    ps.setInt(2, month);
                    ps.setInt(3, year);
                    ps.setInt(4, doanhThuNgay);
                    ps.setInt(5, soBenhNhan);
                    if (doanhThuThang == 0) {
                        ps.setInt(6, 0);
                    } else {
                        ps.setFloat(6, (float) (doanhThuNgay * 100.0 / doanhThuThang));
                    }
                    ps.executeUpdate();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClinicManagement.class.getName()).log(Level.SEVERE, null, ex);
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

        Thoat = new javax.swing.JButton();
        QuenMatKhau = new javax.swing.JLabel();
        NhoMatKhau = new javax.swing.JCheckBox();
        MatKhau = new javax.swing.JPasswordField();
        DangNhap = new javax.swing.JButton();
        TaiKhoan = new customview.PlaceholderTextField();
        BackGround = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 498));
        setMinimumSize(new java.awt.Dimension(800, 498));
        setPreferredSize(new java.awt.Dimension(800, 498));
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Thoat.setBackground(new java.awt.Color(239, 132, 83));
        Thoat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Thoat.setForeground(new java.awt.Color(240, 240, 240));
        Thoat.setText("Thoát");
        Thoat.setActionCommand("Thoat");
        Thoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Thoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThoatMouseClicked(evt);
            }
        });
        Thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThoatActionPerformed(evt);
            }
        });
        getContentPane().add(Thoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 350, 120, 40));

        QuenMatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        QuenMatKhau.setForeground(new java.awt.Color(51, 51, 255));
        QuenMatKhau.setText("<HTML><U>Quên mật khẩu</U></HTML>");
        QuenMatKhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        QuenMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                QuenMatKhauMouseClicked(evt);
            }
        });
        getContentPane().add(QuenMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 300, -1, -1));

        NhoMatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        NhoMatKhau.setText("Nhớ mật khẩu");
        getContentPane().add(NhoMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, -1, -1));

        MatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        MatKhau.setText("Mật khẩu");
        MatKhau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                MatKhauFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                MatKhauFocusLost(evt);
            }
        });
        getContentPane().add(MatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 320, -1));

        DangNhap.setBackground(new java.awt.Color(239, 132, 83));
        DangNhap.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DangNhap.setForeground(new java.awt.Color(240, 240, 240));
        DangNhap.setText("Đăng nhập");
        DangNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DangNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DangNhapMouseClicked(evt);
            }
        });
        DangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DangNhapActionPerformed(evt);
            }
        });
        getContentPane().add(DangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 120, 40));

        TaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TaiKhoan.setPlaceholder("Tài khoản");
        getContentPane().add(TaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 320, -1));

        BackGround.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BackGround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277314017_1607857956260423_4203521708542983050_n.png"))); // NOI18N
        BackGround.setMaximumSize(new java.awt.Dimension(800, 535));
        BackGround.setMinimumSize(new java.awt.Dimension(800, 535));
        BackGround.setPreferredSize(new java.awt.Dimension(800, 535));
        getContentPane().add(BackGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -100, 1240, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
       
    }//GEN-LAST:event_formKeyTyped

    private void DangNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangNhapMouseClicked
       
    }//GEN-LAST:event_DangNhapMouseClicked

    private void ThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThoatMouseClicked
        
    }//GEN-LAST:event_ThoatMouseClicked

    private void MatKhauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MatKhauFocusGained

        MatKhau.setEchoChar('\u25CF');
        String password = String.valueOf(MatKhau.getPassword());

        if (password.equals("Mật khẩu")) {
            MatKhau.setText("");
            MatKhau.setForeground(Color.black);
        }
    }//GEN-LAST:event_MatKhauFocusGained

    private void MatKhauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MatKhauFocusLost
        String password = String.valueOf(MatKhau.getPassword());

        if (password.equals("password") || password.toLowerCase().equals("")) {
            MatKhau.setText("Mật khẩu");
            MatKhau.setEchoChar((char) 0);
            MatKhau.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_MatKhauFocusLost

    private void QuenMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_QuenMatKhauMouseClicked
        if ("".equals(TaiKhoan.getText())) {
            JOptionPane.showMessageDialog(null, "Hãy nhập thông tin vào ô tài khoản", "Cảnh báo", ERROR_MESSAGE);
        } else {
            try {
                DatabaseConnection DTBC = new DatabaseConnection();
                Connection conn = DTBC.getConnection(this);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT CMND FROM NHANVIEN "
                        + "WHERE CMND = '" + TaiKhoan.getText() + "'");
                if (rs.next()) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            ForgotPassword dialog = new ForgotPassword(new javax.swing.JFrame(), true, TaiKhoan.getText());
                            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                            for (WindowListener wl : dialog.getWindowListeners()) {
                                dialog.removeWindowListener(wl);
                            }
                            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                @Override
                                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                    dialog.dispose();
                                    ClinicManagement cm = new ClinicManagement();
                                    cm.setVisible(true);
                                }
                            });
                            dialog.setVisible(true);
                        }
                    });
                    this.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Không có tài khoản tương tự", "Lỗi", ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_QuenMatKhauMouseClicked

    private void DangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DangNhapActionPerformed
        if ("admin".equals(TaiKhoan.getText()) && "123".equals(String.valueOf(MatKhau.getPassword()))) {
            Home mf = new Home("admin");
            mf.setVisible(true);
            this.dispose();
        } else {
            try {
                DatabaseConnection DTBC = new DatabaseConnection();
                Connection conn = DTBC.getConnection(this);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT CMND, MatKhau FROM NHANVIEN "
                        + "WHERE CMND = '" + TaiKhoan.getText() + "'");
                if (rs.next()) {
                    String password = rs.getString("MatKhau");

                    try {
                        String decryptedText = DecryptPassword(password);
                        if (String.valueOf(MatKhau.getPassword()) == null ? decryptedText == null : String.valueOf(MatKhau.getPassword()).equals(decryptedText)) {
                            if (NhoMatKhau.isSelected()) {
                                Writer output = new BufferedWriter(new FileWriter("temp.log", false));
                                output.append("true\r\n");
                                output.append(TaiKhoan.getText() + "\r\n");
                                output.append(String.valueOf(MatKhau.getPassword()));
                                output.close();
                            }
                            Home mf = new Home(TaiKhoan.getText());
                            mf.setVisible(true);
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Sai mật khẩu", "Sai mật khẩu", ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi mật khẩu", ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tên đăng nhập", "Đăng nhập thất bại", 2);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_DangNhapActionPerformed

    private void ThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThoatActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_ThoatActionPerformed

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
            java.util.logging.Logger.getLogger(ClinicManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClinicManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClinicManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClinicManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ClinicManagement().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackGround;
    private javax.swing.JButton DangNhap;
    private javax.swing.JPasswordField MatKhau;
    private javax.swing.JCheckBox NhoMatKhau;
    private javax.swing.JLabel QuenMatKhau;
    private customview.PlaceholderTextField TaiKhoan;
    private javax.swing.JButton Thoat;
    // End of variables declaration//GEN-END:variables
}
