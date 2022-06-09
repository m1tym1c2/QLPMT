/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package clinicmanagement;

import static clinicmanagement.ForgotPassword.cipher;
import static clinicmanagement.ForgotPassword.encrypt;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author ngoctienTNT
 */
public class AddStaff extends javax.swing.JDialog {

    /**
     * Creates new form AddStaff
     *
     * @param parent
     * @param modal
     */
    private static String CMND = "";
    private String filename = "";
    private File f;
    String encodedKey = "1v9BaydNlB2uI58JTO4HlQ==";

    public AddStaff(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        getContentPane().setBackground(new Color(235, 235, 235));
    }

    public AddStaff(java.awt.Frame parent, boolean modal, String CMND) {
        super(parent, modal);
        initComponents();
        getContentPane().setBackground(new Color(235, 235, 235));
        this.CMND = CMND;
        String dd = "2002-01-01";
        String ddd = "2002-12-31";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(ddd);
            FNgaySinh.setDate(date);
            FNgaySinh.setMaxSelectableDate(date1);
        } catch (ParseException ex) {
            Logger.getLogger(AddStaff.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataRetrive();
    }

    public String EncryptPassword(String s) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        cipher = Cipher.getInstance("AES");
        return encrypt(s, originalKey);
    }

    public static String encrypt(String plainText, SecretKey secretKey)
            throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    private void DataRetrive() {
        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            boolean flag = true;
            int num = 1;
            ResultSet rs;
            while (flag) {
                if (num < 10) {
                    MaNV.setText("PMT00" + num);
                } else if (num < 100) {
                    MaNV.setText("PMT0" + num);
                } else {
                    MaNV.setText("PMT" + num);
                }
                rs = stm.executeQuery("select manhanvien from nhanvien where exists (select manhanvien from nhanvien where manhanvien='" + MaNV.getText()+"')");
                if (rs.next() == false)
                    flag=false;
                else
                    num++;
            }
            rs = stm.executeQuery("SELECT * FROM CHUCNANG WHERE MaChucNang <> '000'");
            while (rs.next()) {
                Combobox.addItem(rs.getString("TenChucNang"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }

    public static BufferedImage scaleImage(int w, int h, BufferedImage img) throws Exception {
        BufferedImage bi;
        bi = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, w, h, null);
        g2d.dispose();
        return bi;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        MaNV = new javax.swing.JLabel();
        HoTen = new javax.swing.JTextField();
        FCMND = new javax.swing.JTextField();
        Email = new javax.swing.JTextField();
        Luong = new javax.swing.JTextField();
        Combobox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        FNgaySinh = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        HeSo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Them moi nhan vien");
        setMinimumSize(new java.awt.Dimension(650, 420));
        setPreferredSize(new java.awt.Dimension(798, 552));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(1, 84, 43));
        jLabel1.setText("THÊM MỚI NHÂN VIÊN");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 166, 84));
        jLabel2.setText("Họ và tên:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 166, 84));
        jLabel3.setText("CMND:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 166, 84));
        jLabel5.setText("Địa chỉ:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 210, 80, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 166, 84));
        jLabel6.setText("Chức vụ:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 280, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 166, 84));
        jLabel7.setText("Email:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 166, 84));
        jLabel8.setText("Lương CB:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 360, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 166, 84));
        jLabel10.setText("Mã NV:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, -1, -1));

        MaNV.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        MaNV.setForeground(new java.awt.Color(0, 94, 105));
        MaNV.setText("aaa");
        getContentPane().add(MaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, -1, -1));

        HoTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        HoTen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(HoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, 270, 28));

        FCMND.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        FCMND.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(FCMND, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, 270, 28));

        Email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 320, 270, 28));

        Luong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Luong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(Luong, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 360, 270, 28));

        Combobox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(Combobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 280, 270, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 166, 84));
        jLabel9.setText("Ngày sinh:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, -1));

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 99, 28));
        jButton1.setText("Thêm");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, 150, 40));

        FNgaySinh.setDateFormatString("dd-MM-yyyy");
        FNgaySinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        FNgaySinh.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FNgaySinhPropertyChange(evt);
            }
        });
        getContentPane().add(FNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, 230, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 200, 270, 70));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278586313_488568276388189_6242802706683906901_n.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 318, 318));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 320, 320));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 166, 84));
        jLabel11.setText("Hệ số:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, -1, -1));

        HeSo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        HeSo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(HeSo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 400, 270, 28));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void FNgaySinhPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FNgaySinhPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_FNgaySinhPropertyChange

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setMultiSelectionEnabled(false);
        FileFilter imageFilter = new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes());
        chooser.setFileFilter(imageFilter);
        int option = chooser.showOpenDialog(this);
        f = chooser.getSelectedFile();
        filename = f.getAbsolutePath();

        try {
            ImageIcon ii = new ImageIcon(scaleImage(318, 318, ImageIO.read(new File(f.getAbsolutePath()))));//get the image from file chooser and scale it to match JLabel size
            jLabel4.setIcon(ii);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            Icon icon = jLabel4.getIcon();
            BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bi.createGraphics();
            icon.paintIcon(jLabel4, g2d, 0, 0);
            g2d.dispose();
            ResultSet rs;
            if (icon == null) {
                JOptionPane.showMessageDialog(this, "", "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }

            File file = new File("src\\assets\\" + MaNV.getText().trim() + ".png");
            try {
                ImageIO.write(bi, "png", file);
            } catch (Exception ex) {

            }
            String ep = "";
            try {
                ep = EncryptPassword(String.valueOf(FCMND.getText()));
            } catch (Exception ex) {
                Logger.getLogger(AddStaff.class.getName()).log(Level.SEVERE, null, ex);
            }
            SimpleDateFormat si = new SimpleDateFormat("YYYY-MM-dd HH:mm:SS");
            if ("".equals(HoTen.getText())) {
                JOptionPane.showMessageDialog(this, "Không được để trống họ tên!!!", "Lỗi", ERROR_MESSAGE);
                return;
            }
            if ("".equals(FCMND.getText())) {
                JOptionPane.showMessageDialog(this, "Không được để trống CMND!!!", "Lỗi", ERROR_MESSAGE);
                return;
            }
            if ("".equals(Email.getText())) {
                JOptionPane.showMessageDialog(this, "Không được để trống email!!!", "Lỗi", ERROR_MESSAGE);
                return;
            }
            if ("".equals(Luong.getText())) {
                JOptionPane.showMessageDialog(this, "Không được để trống lương!!!", "Lỗi", ERROR_MESSAGE);
                return;
            }
            rs = stm.executeQuery("select CMND from nhanvien where exists (select CMND from nhanvien where CMND='" + FCMND.getText()+"')");
            if (rs.next() == true) {
                JOptionPane.showMessageDialog(this, "Trùng CMND!!!", "Lỗi", ERROR_MESSAGE);
                return;
            }
            String MaChucNang = "";
            switch (Combobox.getSelectedIndex()) {
                case 0:
                    MaChucNang = "001";
                    break;
                case 1:
                    MaChucNang = "002";
                    break;
                case 2:
                    MaChucNang = "003";
                    break;
                case 3:
                    MaChucNang = "004";
                    break;
                default:
                    break;
            }
            stm.execute("insert into nhanvien values('" + MaNV.getText() + "','" + FCMND.getText() + "','" + si.format(FNgaySinh.getDate()) + "',N'"
                    + jTextArea1.getText() + "','" + Email.getText() + "'," + Luong.getText() + "," + HeSo.getText() + ",N'/assets/" + MaNV.getText().trim() + ".png" + "','"
                    + ep + "',N'" + HoTen.getText() + "',0)");
            stm.execute("insert into phanquyen values('" + MaNV.getText() + "','" + MaChucNang + "')");
            JOptionPane.showMessageDialog(this, "Đã tạo tài khoản thành công");
            this.dispose();
            EmployeeManager em = new EmployeeManager(CMND);
            em.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(AddStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            AddStaff dialog = new AddStaff(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> Combobox;
    private javax.swing.JTextField Email;
    private javax.swing.JTextField FCMND;
    private com.toedter.calendar.JDateChooser FNgaySinh;
    private javax.swing.JTextField HeSo;
    private javax.swing.JTextField HoTen;
    private javax.swing.JTextField Luong;
    private javax.swing.JLabel MaNV;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
