/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicmanagement;

import static clinicmanagement.Home.scaleImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Dell
 */
public class LuongNhanVien extends javax.swing.JFrame {

    private static String CMND = "";
    private boolean User = false;
    private String MaNV = "";

    /**
     * Creates new form LuongNhanVien
     */
    public LuongNhanVien() {
        initComponents();
        getContentPane().setBackground(Color.white);
        jPanel3.setVisible(false);

    }

    public LuongNhanVien(String CMND) {
        initComponents();
        this.CMND = CMND;
        getContentPane().setBackground(Color.white);
        jPanel3.setVisible(false);
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));
        getContentPane().setBackground(Color.white);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        RetriveData();
        InitData();
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
                ResultSet rs = stm.executeQuery("SELECT TenNhanVien, HinhAnh FROM NHANVIEN "
                        + "WHERE CMND = '" + CMND + "'");
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
                        ImageIcon iconn = new ImageIcon(masked);
                        Anhdaidien.setIcon(iconn);
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

    private void InitData() {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.setRowCount(0);
        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT TenNhanVien, MaNhanVien, LuongCB, HeSo, TienThuong FROM NHANVIEN");
            SimpleDateFormat simpDate = new SimpleDateFormat("yyyy");
            while (rs.next()) {
                Vector row = new Vector();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                row.add(model.getRowCount() + 1);
                row.add(rs.getString("MaNhanVien"));
                row.add(rs.getString("TenNhanVien"));
                row.add(String.valueOf(rs.getInt("LuongCB")));
                row.add(String.valueOf(rs.getInt("HeSo")));
                row.add(String.valueOf(rs.getInt("TienThuong")));
                row.add(String.valueOf(rs.getInt("LuongCB") * rs.getInt("HeSo") + rs.getInt("TienThuong")));
                model.getRowCount();
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
        int sum =0;
        for (int i = 0; i < table.getRowCount(); i++) {
            int Amount = Integer.parseInt(table.getValueAt(i, 6)+"");
            sum+=Amount;
        }
        jLabel6.setText(String.valueOf(sum) + "VNĐ");
        }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        trolai = new javax.swing.JButton();
        in = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Anhdaidien = new javax.swing.JLabel();
        Tentaikhoan = new javax.swing.JLabel();
        Nutmuiten = new javax.swing.JLabel();
        Anhdautrang = new javax.swing.JLabel();
        Tentrang = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        placeholderTextField1 = new customview.PlaceholderTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        Tentrang1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 11, -1, 49));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 14, -1, -1));

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
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 69, 201, 46));

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
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 121, 200, 46));
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 65, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278367228_415742123260148_2336724036194180860_n.png"))); // NOI18N
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 121, 54, 46));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277367234_720289712438638_7547041272784298626_n.png"))); // NOI18N
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 65, -1, 50));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, 280, 180));

        trolai.setBackground(new java.awt.Color(255, 204, 204));
        trolai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        trolai.setForeground(new java.awt.Color(0, 99, 28));
        trolai.setText("Quay lại");
        trolai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trolaiActionPerformed(evt);
            }
        });
        jPanel1.add(trolai, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 510, 192, 36));

        in.setBackground(new java.awt.Color(255, 204, 204));
        in.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        in.setForeground(new java.awt.Color(0, 99, 28));
        in.setText("In bảng tính lương");
        jPanel1.add(in, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 510, 192, 36));

        jPanel2.setBackground(new java.awt.Color(208, 242, 224));
        jPanel2.setPreferredSize(new java.awt.Dimension(973, 87));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Anhdaidien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/image 6.png"))); // NOI18N
        jPanel2.add(Anhdaidien, new org.netbeans.lib.awtextra.AbsoluteConstraints(716, 0, -1, -1));

        Tentaikhoan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentaikhoan.setForeground(new java.awt.Color(0, 84, 42));
        Tentaikhoan.setText("Lê Phi Long\n");
        jPanel2.add(Tentaikhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 26, -1, -1));

        Nutmuiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Screenshot 2022-04-26 103146.png"))); // NOI18N
        Nutmuiten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NutmuitenMouseClicked(evt);
            }
        });
        jPanel2.add(Nutmuiten, new org.netbeans.lib.awtextra.AbsoluteConstraints(936, 26, 44, -1));

        Anhdautrang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Untitled-2.png"))); // NOI18N
        jPanel2.add(Anhdautrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        Tentrang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentrang.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang.setText("QUẢN LÝ NHÂN VIÊN\n");
        jPanel2.add(Tentrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 19, -1, 36));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277027184_555937372561581_5654092174016176725_n.png"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));

        placeholderTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        placeholderTextField1.setPlaceholder("Tìm kiếm...");
        jPanel2.add(placeholderTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 346, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 70));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "Lương cơ bản", "Hệ số", "Tiền thưởng", "Tổng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablePropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(50);
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(1).setMinWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setMaxWidth(150);
            table.getColumnModel().getColumn(2).setMinWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setMaxWidth(150);
            table.getColumnModel().getColumn(3).setMinWidth(150);
            table.getColumnModel().getColumn(3).setPreferredWidth(150);
            table.getColumnModel().getColumn(3).setMaxWidth(150);
            table.getColumnModel().getColumn(4).setMinWidth(100);
            table.getColumnModel().getColumn(4).setMaxWidth(100);
            table.getColumnModel().getColumn(5).setMinWidth(100);
            table.getColumnModel().getColumn(5).setPreferredWidth(100);
            table.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 870, 230));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 84, 42));
        jLabel2.setText("Năm:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(552, 164, 60, 25));

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022" }));
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 160, 171, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 84, 42));
        jLabel1.setText("Tháng:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 168, 70, -1));

        Tentrang1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        Tentrang1.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang1.setText("BẢNG LƯƠNG NHÂN VIÊN\n");
        jPanel1.add(Tentrang1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 105, 468, -1));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 171, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 99, 28));
        jLabel5.setText("Tổng: ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 460, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 0, 153));
        jLabel6.setText("Tổng: ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NutmuitenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NutmuitenMouseClicked
        if (User == false) {
            jPanel3.setVisible(true);
            User = true;
        } else {
            jPanel3.setVisible(false);
            User = false;
        }      // TODO add your handling code here:
    }//GEN-LAST:event_NutmuitenMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        java.awt.EventQueue.invokeLater(() -> {
            UserInformation dialog = new UserInformation(new javax.swing.JFrame(), true);
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            for (WindowListener wl : dialog.getWindowListeners()) {
                dialog.removeWindowListener(wl);
            }
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    dialog.dispose();
                    MedicineUsageManagement frame = new MedicineUsageManagement();
                    frame.setVisible(true);
                }
            });
            dialog.setVisible(true);
        });
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ClinicManagement form = new ClinicManagement();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

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
                        Home frame = new Home(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablePropertyChange

    }//GEN-LAST:event_tablePropertyChange

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        JTable source = (JTable) evt.getSource();
        int row = source.rowAtPoint(evt.getPoint());
        String m = JOptionPane.showInputDialog("Nhập tiền thưởng: ");
        int t = Integer.parseInt(m);
        String MANV = source.getModel().getValueAt(row, 1) + "";
        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            stm.execute("Update nhanvien set TienThuong = " + t + " where manhanvien = '" + MANV + "'");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
        InitData();
    }//GEN-LAST:event_tableMouseClicked

    private void trolaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trolaiActionPerformed
        EmployeeManager form = new EmployeeManager(CMND);
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_trolaiActionPerformed

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
            java.util.logging.Logger.getLogger(LuongNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LuongNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LuongNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LuongNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LuongNhanVien().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JLabel Anhdautrang;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JLabel Tentrang;
    private javax.swing.JLabel Tentrang1;
    private javax.swing.JButton in;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private customview.PlaceholderTextField placeholderTextField1;
    private javax.swing.JTable table;
    private javax.swing.JButton trolai;
    // End of variables declaration//GEN-END:variables
}
