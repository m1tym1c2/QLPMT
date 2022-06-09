/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicmanagement;

import static clinicmanagement.Home.scaleImage;
import java.awt.AlphaComposite;
import java.sql.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultCaret;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 *
 * @author ASUS
 */
public class AddNewMedicine extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    private boolean User = false;
    private String filename = "";
    private String CMND = "";
    private File f;

    public AddNewMedicine() {
        initComponents();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.white);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        jPanel1.setVisible(false);

        try {
            LoadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Chưa có cách dùng/ đơn vị tính, bạn vui lòng thêm cách dùng trước", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public AddNewMedicine(String CMND) {
        initComponents();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.white);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        jPanel1.setVisible(false);
        this.CMND = CMND;
        try {
            LoadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Chưa có cách dùng/ đơn vị tính, bạn vui lòng thêm cách dùng trước", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        RetriveData();
    }

    public void LoadData() throws SQLException {

        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();

        ResultSet rs = stm.executeQuery("SELECT TenCachDung FROM CACHDUNG;");
        while (rs.next()) {
            cachdung.addItem(rs.getString("TenCachDung"));
        }

        rs = stm.executeQuery("SELECT TenDonViTinh FROM DONVITINH;");
        while (rs.next()) {
            Donvi.addItem(rs.getString("TenDonViTinh"));
        }
        //Donvi.setSelectedIndex(1);
        rs.close();
        stm.close();
        conn.close();
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

        jPanel2 = new javax.swing.JPanel();
        Anhdaidien = new javax.swing.JLabel();
        Tentaikhoan = new javax.swing.JLabel();
        Nutmuiten = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        Luu = new javax.swing.JButton();
        cachdung = new javax.swing.JComboBox<>();
        Donvi = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setForeground(java.awt.Color.white);
        setMinimumSize(new java.awt.Dimension(822, 618));
        setResizable(false);
        setSize(new java.awt.Dimension(990, 800));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(209, 242, 225));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Anhdaidien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/image 6.png"))); // NOI18N
        jPanel2.add(Anhdaidien, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 13, -1, -1));

        Tentaikhoan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentaikhoan.setForeground(new java.awt.Color(0, 84, 42));
        Tentaikhoan.setText("Lê Phi Long\n");
        jPanel2.add(Tentaikhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, -1, -1));

        Nutmuiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Screenshot 2022-04-26 103146.png"))); // NOI18N
        Nutmuiten.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Nutmuiten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NutmuitenMouseClicked(evt);
            }
        });
        jPanel2.add(Nutmuiten, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 30, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277690175_419076512892132_8107806418371628641_n.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 80));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 1000, 80));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(0, 166, 84));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Thông tin cá nhân");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 12, -1, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jButton4.setBackground(new java.awt.Color(0, 166, 84));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Đổi mật khẩu");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 197, 46));

        jButton5.setBackground(new java.awt.Color(242, 111, 51));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Đăng xuất");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 197, 46));
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 72, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278367228_415742123260148_2336724036194180860_n.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 54, 46));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277367234_720289712438638_7547041272784298626_n.png"))); // NOI18N
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 55));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, 280, 180));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(1, 84, 43));
        jLabel14.setText("THÊM LOẠI THUỐC MỚI");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278586313_488568276388189_6242802706683906901_n.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(1, 84, 43));
        jLabel4.setText("Cách dùng:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(1, 84, 43));
        jLabel5.setText("Tên thuốc: ");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(1, 84, 43));
        jLabel6.setText("Đơn vị tính: ");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, -1, -1));

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 165, 320, 40));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(1, 84, 43));
        jLabel7.setText("Loại thuốc:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, -1, -1));

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 285, 320, 40));

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Quay lại");
        jButton2.setToolTipText("");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 500, 160, 40));

        Luu.setBackground(new java.awt.Color(255, 204, 204));
        Luu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Luu.setForeground(new java.awt.Color(0, 99, 28));
        Luu.setText("Lưu thông tin");
        Luu.setToolTipText("");
        Luu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Luu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LuuMouseClicked(evt);
            }
        });
        Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LuuActionPerformed(evt);
            }
        });
        getContentPane().add(Luu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 500, 160, 40));

        cachdung.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cachdung.setMaximumRowCount(20);
        cachdung.setBorder(null);
        getContentPane().add(cachdung, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 340, 320, 40));

        Donvi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Donvi.setBorder(null);
        getContentPane().add(Donvi, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 220, 320, 40));

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

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserInformation dialog = new UserInformation(new javax.swing.JFrame(), true);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                for (WindowListener wl : dialog.getWindowListeners()) {
                    dialog.removeWindowListener(wl);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        dialog.dispose();
                        AddNewMedicine frame = new AddNewMedicine();
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ClinicManagement form = new ClinicManagement();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setMultiSelectionEnabled(false);
        FileFilter imageFilter = new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes());
        chooser.setFileFilter(imageFilter);
        int option = chooser.showOpenDialog(this);
        f = chooser.getSelectedFile();
        filename = f.getAbsolutePath();
        //jTextField1.setText(filename);
        try {
            ImageIcon ii = new ImageIcon(scaleImage(400, 400, ImageIO.read(new File(f.getAbsolutePath()))));//get the image from file chooser and scale it to match JLabel size
            jLabel3.setIcon(ii);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_jLabel3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        MedicineUsageManagement frame = new MedicineUsageManagement();
        this.dispose();
        frame.setVisible(true);
    }//GEN-LAST:event_jButton2MouseClicked
    public int LUU() throws SQLException {
        if (jTextField2.getText() == null || jTextField3.getText() == null || filename == "") {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đủ thông tin! ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();

        String donvi = Donvi.getSelectedItem().toString();
        //thêm đơn vị tính
        ResultSet rs;

        //thêm cách dùng
        String cd = cachdung.getSelectedItem().toString();
        rs = stm.executeQuery("SELECT MaCachDung, TenCachDung from CACHDUNG   ");
        String macachdung = "";
        while (rs.next()) {
            if (rs.getString("TenCachDung").equals(cd)) {
                macachdung = rs.getString("MaCachDung");
            }
        }
        //theem thuoc
        String tenthuoc = jTextField2.getText();
        rs = stm.executeQuery("SELECT TenThuoc from THUOC   ");
        boolean kt = false;
        int sothuoc = 0;
        while (rs.next()) {
            if (rs.getString("TenThuoc").equals(tenthuoc)) {
                kt = true;
            }
            sothuoc++;
        }
        String mathuoc = "T" + String.valueOf(sothuoc + 1);

        rs = stm.executeQuery("SELECT MaCachDung from CACHDUNG   ");
        while (rs.next()) {
            if (rs.getString("MaCachDung").equals(mathuoc)) {
                sothuoc++;
                mathuoc = "CD" + String.valueOf(sothuoc + 1);
            }
        }
        if (!kt) {
            stm.executeUpdate("INSERT INTO THUOC  VALUES ( N'" + mathuoc + "',N'" + tenthuoc + "',N'" + donvi + "','" + 0 + "',N'" + jTextField3.getText() + "',N'" + macachdung + "',N'" + f.getAbsolutePath() + "');");
        } else {
            JOptionPane.showMessageDialog(this, "Thuốc này đã có", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }
        rs.close();
        stm.close();
        conn.close();
        return 1;
    }
    private void LuuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LuuMouseClicked


    }//GEN-LAST:event_LuuMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
                        AddNewMedicine frame = new AddNewMedicine(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        MedicineUsageManagement frame = new MedicineUsageManagement(CMND);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if ("admin".equals(CMND)) {
            JOptionPane.showMessageDialog(this, "admin không thể đổi mật khẩu", "Lỗi", ERROR_MESSAGE);
        } else {

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
                            AddNewMedicine frame = new AddNewMedicine(CMND);
                            frame.setVisible(true);
                        }
                    });
                    dialog.setVisible(true);
                }
            });
            this.dispose();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void LuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LuuActionPerformed
        try {
            if (LUU() == 1) {
                JOptionPane.showMessageDialog(this, "Bạn đã lưu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new MedicineUsageManagement().setVisible(true);
                    }
                });
                this.dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_LuuActionPerformed

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
            java.util.logging.Logger.getLogger(AddNewMedicine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNewMedicine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNewMedicine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNewMedicine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddNewMedicine("1111").setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JComboBox<String> Donvi;
    private javax.swing.JButton Luu;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JComboBox<String> cachdung;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
