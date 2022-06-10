/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicmanagement;

import static clinicmanagement.Home.scaleImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Dell
 */
public class DanhSachKhamBenh extends javax.swing.JFrame {

    private static String CMND = "";
    private boolean User = false;
    /**
     * Creates new form DanhSachKhamBenh
     */
    public DanhSachKhamBenh() {
        initComponents();
        this.setLocationRelativeTo(null);
        try
        {
            HienThi();
        }
        catch (Exception e)
                {
                    
                }
        NgayThangNam.setText("Ngày "+LocalDate.now().getDayOfMonth() + " Tháng "+LocalDate.now().getMonthValue() + " Năm " + LocalDate.now().getYear());
        jPanel4.setVisible(false);
    }
    
    public DanhSachKhamBenh(String CMND) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.CMND = CMND;
        try
        {
            HienThi();
        }
        catch (Exception e)
                {
                    
                }
        
        JTableHeader header = Table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(Table));
        getContentPane().setBackground(Color.white);
        NgayThangNam.setText("Ngày "+LocalDate.now().getDayOfMonth() + " Tháng "+LocalDate.now().getMonthValue() + " Năm " + LocalDate.now().getYear());
        jPanel4.setVisible(false);
        RetriveData();
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
    
    private void HienThi() throws SQLException {
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();
        String Ngay = String.valueOf(LocalDate.now().getDayOfMonth());
        String Thang = String.valueOf(LocalDate.now().getMonthValue());
        String Nam = String.valueOf(LocalDate.now().getYear());
        ResultSet rs = stm.executeQuery("SELECT BENHNHAN.MaBenhNhan, TenBenhNhan, GioiTinh, NamSinh, DiaChi, MaNhanVien "
                + "                      FROM BENHNHAN, PHIEUKHAMBENH "
                + "                      WHERE BENHNHAN.MaBenhNhan = PHIEUKHAMBENH.MaBenhNhan "
                + "                      AND DAY(NgayKham) = " + Ngay  
                + "                      AND MONTH(NgayKham) = " + Thang
                + "                      AND YEAR(NgayKham) = " + Nam);
        while (rs.next())
        {
            Vector row = new Vector();
            DefaultTableModel model = (DefaultTableModel) Table.getModel();
            
            row.add(model.getRowCount()+1);
            row.add(rs.getString("MaBenhNhan"));
            row.add(rs.getString("TenBenhNhan"));
            row.add(rs.getString("GioiTinh"));
            row.add(String.valueOf(rs.getInt("NamSinh")));
            row.add(rs.getString("DiaChi"));
            if (rs.getObject("MaNhanVien")==null) row.add("Chưa khám");
            else row.add("Đã khám");
            model.getRowCount();
            model.addRow(row);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        NgayThangNam = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        Anhdautrang = new javax.swing.JLabel();
        Tentrang = new javax.swing.JLabel();
        Tentaikhoan = new javax.swing.JLabel();
        Anhdaidien = new javax.swing.JLabel();
        Nutmuiten = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        placeholderTextField1 = new customview.PlaceholderTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        Tentrang2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1171, 700));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1171, 700));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setBackground(new java.awt.Color(0, 166, 84));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Thông tin cá nhân");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 11, 200, 49));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 14, -1, -1));

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
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 69, 200, 46));

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
        jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 121, 200, 46));
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 65, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278367228_415742123260148_2336724036194180860_n.png"))); // NOI18N
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 121, 54, 46));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277367234_720289712438638_7547041272784298626_n.png"))); // NOI18N
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 65, -1, 50));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, 280, 180));

        NgayThangNam.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        NgayThangNam.setForeground(new java.awt.Color(0, 84, 42));
        NgayThangNam.setText("Ngày 25 tháng 04 năm 2022\n");
        jPanel1.add(NgayThangNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 154, 279, 34));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã bệnh nhân", "Họ tên", "Giới tính", "Năm sinh", "Địa chỉ", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setPreferredWidth(50);
            Table.getColumnModel().getColumn(0).setMaxWidth(50);
            Table.getColumnModel().getColumn(1).setPreferredWidth(100);
            Table.getColumnModel().getColumn(1).setMaxWidth(150);
            Table.getColumnModel().getColumn(2).setPreferredWidth(200);
            Table.getColumnModel().getColumn(2).setMaxWidth(200);
            Table.getColumnModel().getColumn(3).setPreferredWidth(100);
            Table.getColumnModel().getColumn(3).setMaxWidth(100);
            Table.getColumnModel().getColumn(4).setPreferredWidth(100);
            Table.getColumnModel().getColumn(4).setMaxWidth(150);
            Table.getColumnModel().getColumn(5).setPreferredWidth(250);
            Table.getColumnModel().getColumn(6).setPreferredWidth(100);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 214, 969, 325));

        jPanel3.setBackground(new java.awt.Color(208, 242, 224));

        Anhdautrang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Untitled-5 1.png"))); // NOI18N
        Anhdautrang.setText("jLabel5");

        Tentrang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentrang.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang.setText("DANH SÁCH KHÁM BỆNH");

        Tentaikhoan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentaikhoan.setForeground(new java.awt.Color(0, 84, 42));
        Tentaikhoan.setText("Lê Phi Long\n");

        Anhdaidien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/image 6.png"))); // NOI18N

        Nutmuiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Screenshot 2022-04-26 103146.png"))); // NOI18N
        Nutmuiten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NutmuitenMouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277027184_555937372561581_5654092174016176725_n.png"))); // NOI18N

        placeholderTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        placeholderTextField1.setPlaceholder("Tìm kiếm...");
        placeholderTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeholderTextField1ActionPerformed(evt);
            }
        });
        placeholderTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                placeholderTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(Anhdautrang, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tentrang, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(375, 375, 375)
                        .addComponent(jLabel5))
                    .addComponent(placeholderTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(Anhdaidien, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Tentaikhoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Nutmuiten)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Anhdaidien, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Anhdautrang, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(5, 5, 5)
                                            .addComponent(jLabel5))
                                        .addComponent(placeholderTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Tentrang))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Tentaikhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(Nutmuiten)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1181, 70));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Group 34.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 320, 65, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Group 32.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 230, 65, -1));

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Quay lại");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 580, 216, 36));

        Tentrang2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Tentrang2.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang2.setText("DANH SÁCH KHÁM BỆNH");
        jPanel1.add(Tentrang2, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 102, 462, 42));

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 99, 28));
        jButton1.setText("Tra cứu bệnh nhân");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 580, 214, 36));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PatientLookup dialog = new PatientLookup(CMND);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                for (WindowListener wl : dialog.getWindowListeners()) {
                    dialog.removeWindowListener(wl);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        dialog.dispose();
                        DanhSachKhamBenh frame = new DanhSachKhamBenh(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        if (Table.getSelectedRow() == -1)
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một bệnh nhân", "Cảnh báo chưa chọn bệnh nhân", 2);
        else {
            PhieuKhamBenh.MoLanDau = true;
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    PhieuKhamBenh.TenBenhNhan = model.getValueAt(Table.getSelectedRow(), 2).toString();
                    PhieuKhamBenh.MaBenhNhan = model.getValueAt(Table.getSelectedRow(), 1).toString();
                    PhieuKhamBenh dialog = new PhieuKhamBenh(CMND);
                    dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    for (WindowListener wl : dialog.getWindowListeners()) {
                        dialog.removeWindowListener(wl);
                    }
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            dialog.dispose();
                            DanhSachKhamBenh frame = new DanhSachKhamBenh(CMND);
                            frame.setVisible(true);
                        }
                    });
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
            });
            this.dispose();
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void NutmuitenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NutmuitenMouseClicked
        if (User == false) {
            jPanel4.setVisible(true);
            User = true;
        } else {
            jPanel4.setVisible(false);
            User = false;
        }          // TODO add your handling code here:
    }//GEN-LAST:event_NutmuitenMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ClinicManagement form = new ClinicManagement();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
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
                        DanhSachKhamBenh frame = new DanhSachKhamBenh(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        int SoBenhNhanToiDa = 0;
        try
        {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT GiaTri FROM THAMSO WHERE TenThamSo = 'SoBenhNhanToiDa'");
            if (rs.next()) SoBenhNhanToiDa = rs.getInt(1);
        }
        catch (Exception e)
        {
            
        }
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        if (SoBenhNhanToiDa<=model.getRowCount())
        {
            JOptionPane.showMessageDialog(this, "Số lượng bệnh nhân đã đạt tối đa trong ngày", "Lỗi thêm bệnh nhân", 0);
            return;
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ThemBenhNhan dialog = new ThemBenhNhan(CMND);
                    dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    for (WindowListener wl : dialog.getWindowListeners()) {
                        dialog.removeWindowListener(wl);
                    }
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            dialog.dispose();
                            DanhSachKhamBenh frame = new DanhSachKhamBenh(CMND);
                            frame.setVisible(true);
                        }
                    });
                    dialog.setVisible(true);
                } catch (Exception e) {

                }
            }
        });
        this.dispose();
    }//GEN-LAST:event_jLabel3MousePressed

    private void placeholderTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeholderTextField1ActionPerformed
        
    }//GEN-LAST:event_placeholderTextField1ActionPerformed

    private void placeholderTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placeholderTextField1KeyPressed
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel> (model);
        Table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(placeholderTextField1.getText()));
    }//GEN-LAST:event_placeholderTextField1KeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home(CMND).setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
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
                        DanhSachKhamBenh frame = new DanhSachKhamBenh(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(DanhSachKhamBenh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DanhSachKhamBenh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DanhSachKhamBenh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DanhSachKhamBenh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DanhSachKhamBenh("1111").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JLabel Anhdautrang;
    private javax.swing.JLabel NgayThangNam;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JTable Table;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JLabel Tentrang;
    private javax.swing.JLabel Tentrang2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private customview.PlaceholderTextField placeholderTextField1;
    // End of variables declaration//GEN-END:variables
}
