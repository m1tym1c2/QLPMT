/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicmanagement;

import static clinicmanagement.AddNewMedicine.scaleImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.WindowConstants;

/**
 *
 * @author ASUS
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    private boolean User = false;
    private String CMND = "";
    private String MaChucNang = "";

    public Home() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.white);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        jPanel1.setVisible(false);
    }

    public Home(String s) {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.white);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        jPanel1.setVisible(false);
        CMND = s;
        if (CMND == "admin") {
            MaChucNang = "000";
        }
        RetriveData();
    }

    public void setAccount(String s) {
        CMND = s;
    }

    private void RetriveData() {
        if ("admin".equals(CMND)) {
            ImageIcon iconnull = new ImageIcon(getClass().getResource("/anh/NotSetAvt.png"));
            Anhdaidien.setIcon(iconnull);
            Tentaikhoan.setText("admin");
        } else {
            try {
                DatabaseConnection DTBC = new DatabaseConnection();
                Connection conn = DTBC.getConnection(this);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT TenNhanVien, HinhAnh, NHANVIEN.MaNhanVien, CHUCNANG.MaChucNang, TenChucNang FROM NHANVIEN, CHUCNANG, PHANQUYEN "
                        + "WHERE CMND = '" + CMND + "' AND PHANQUYEN.MaNhanVien = NHANVIEN.MaNhanVien AND CHUCNANG.MaChucNang = PHANQUYEN.MaChucNang");
                if (rs.next()) {
                    Tentaikhoan.setText(rs.getString("TenNhanVien"));
                    MaChucNang = rs.getString("MaChucNang");
                    try {

                        URL url = getClass().getResource(rs.getString("HinhAnh"));
                        File file = new File(url.getPath());
                        BufferedImage master = ImageIO.read(file);
                        try {
                            master = scaleImage(64, 64, master);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        int diameter = Math.min(master.getWidth(), master.getHeight());
                        BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

                        Graphics2D g2d = mask.createGraphics();
                        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
                        g2d.dispose();

                        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
                        g2d = masked.createGraphics();
                        int x = (diameter - master.getWidth()) / 2;
                        int y = (diameter - master.getHeight()) / 2;
                        g2d.drawImage(master, x, y, null);
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
                        g2d.drawImage(mask, 0, 0, null);
                        g2d.dispose();
                        ImageIcon icon = new ImageIcon(masked);
                        Anhdaidien.setIcon(icon);
                    } catch (InvalidPathException | NullPointerException | IOException ex) {
                        ImageIcon iconnull = new ImageIcon(getClass().getResource("/anh/NotSetAvt.png"));
                        Anhdaidien.setIcon(iconnull);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra", "Đăng nhập thất bại", 2);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }
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

        jPanel1 = new javax.swing.JPanel();
        ThongTinCaNhan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        DoiMatKhau = new javax.swing.JButton();
        DangXuat = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Anhdaidien = new javax.swing.JLabel();
        Tentaikhoan = new javax.swing.JLabel();
        Nutmuiten = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setForeground(java.awt.Color.white);
        setMaximumSize(new java.awt.Dimension(822, 618));
        setMinimumSize(new java.awt.Dimension(822, 618));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ThongTinCaNhan.setBackground(new java.awt.Color(0, 166, 84));
        ThongTinCaNhan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ThongTinCaNhan.setForeground(new java.awt.Color(255, 255, 255));
        ThongTinCaNhan.setText("Thông tin cá nhân");
        ThongTinCaNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ThongTinCaNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThongTinCaNhanMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N

        DoiMatKhau.setBackground(new java.awt.Color(0, 166, 84));
        DoiMatKhau.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        DoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        DoiMatKhau.setText("Đổi mật khẩu");
        DoiMatKhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoiMatKhauActionPerformed(evt);
            }
        });

        DangXuat.setBackground(new java.awt.Color(242, 111, 51));
        DangXuat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        DangXuat.setForeground(new java.awt.Color(255, 255, 255));
        DangXuat.setText("Đăng xuất");
        DangXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DangXuatActionPerformed(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278367228_415742123260148_2336724036194180860_n.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277367234_720289712438638_7547041272784298626_n.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(DoiMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ThongTinCaNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(DangXuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel2))
                    .addComponent(ThongTinCaNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 280, 180));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278656380_362998795794715_3573542628940694119_n.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278544340_1296224760868714_6406602451142140332_n.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 99, 28));
        jLabel4.setText("Doanh thu");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 250, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278267157_513258687113783_4908014745777811944_n.png"))); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 99, 28));
        jLabel6.setText("Thuốc");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278600074_664320058001641_213263200731748919_n.png"))); // NOI18N
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 99, 28));
        jLabel8.setText("Nhân viên");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 510, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278667619_5305088552848969_3883622606543918055_n.png"))); // NOI18N
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 99, 28));
        jLabel10.setText("Khám bệnh");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 510, -1, -1));

        jPanel2.setBackground(new java.awt.Color(209, 242, 225));

        Anhdaidien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/image 6.png"))); // NOI18N

        Tentaikhoan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentaikhoan.setForeground(new java.awt.Color(0, 84, 42));
        Tentaikhoan.setText("Lê Phi Long\n");

        Nutmuiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Screenshot 2022-04-26 103146.png"))); // NOI18N
        Nutmuiten.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Nutmuiten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NutmuitenMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(569, Short.MAX_VALUE)
                .addComponent(Anhdaidien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Tentaikhoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Nutmuiten)
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Anhdaidien))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Nutmuiten)
                    .addComponent(Tentaikhoan))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 860, 80));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NutmuitenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NutmuitenMouseClicked
        if (User == false) {
            jPanel1.setVisible(true);
            User = true;
        } else {
            jPanel1.setVisible(false);
            User = false;
        }
    }//GEN-LAST:event_NutmuitenMouseClicked

    private void ThongTinCaNhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThongTinCaNhanMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserInformation dialog = new UserInformation(new javax.swing.JFrame(), true, CMND);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                for (WindowListener wl : dialog.getWindowListeners()) {
                    dialog.removeWindowListener(wl);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        dialog.dispose();
                        Home frame = new Home(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_ThongTinCaNhanMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        if ("000".equals(MaChucNang) || "001".equals(MaChucNang)) {
            MedicineUsageManagement form = new MedicineUsageManagement();
            form.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền vào chức năng này", "Lỗi", 2);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void DangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DangXuatActionPerformed
        try {
            Writer output = new BufferedWriter(new FileWriter("temp.log", false));
            ClinicManagement form;
            form = new ClinicManagement();
            form.setVisible(true);
            this.dispose();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_DangXuatActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        if ("000".equals(MaChucNang) || "002".equals(MaChucNang)) {
            BillList form = new BillList();
            form.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền vào chức năng này", "Lỗi", 2);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        if ("000".equals(MaChucNang) || "003".equals(MaChucNang)) {
            EmployeeManager form = new EmployeeManager(CMND);
            form.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền vào chức năng này", "Lỗi", 2);

        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseClicked

    private void DoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoiMatKhauActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChangePassword dialog = new ChangePassword(new javax.swing.JFrame(), true, CMND);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                for (WindowListener wl : dialog.getWindowListeners()) {
                    dialog.removeWindowListener(wl);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        dialog.dispose();
                        Home frame = new Home(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_DoiMatKhauActionPerformed

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JButton DangXuat;
    private javax.swing.JButton DoiMatKhau;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JButton ThongTinCaNhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
